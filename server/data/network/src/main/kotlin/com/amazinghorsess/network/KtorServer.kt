package com.amazinghorsess.network

import android.util.Log
import com.amazinghorsess.domain.interactor.ServerInteractor
import com.amazinghorsess.domain.manager.ArchiveManager
import com.amazinghorsess.domain.manager.MemoryManager
import com.amazinghorsess.domain.manager.ScanManager
import com.amazinghorsess.domain.model.FileNode
import com.amazinghorsess.domain.model.FileStatus
import com.amazinghorsess.domain.model.ScanResult
import com.amazinghorsess.domain.model.ServerStatus
import com.amazinghorsess.domain.repository.ServerScanRepository

import io.ktor.http.ContentType
import io.ktor.server.application.install
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import java.io.File
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

internal class KtorServer(
    private val scanManager: ScanManager,
    private val memoryManager: MemoryManager,
    private val archiveManager: ArchiveManager,
    private val scanRepository: ServerScanRepository,
) : ServerInteractor, CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private var server: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>? = null
    private val connections = mutableListOf<DefaultWebSocketServerSession>()

    private var scanJob: Job? = null


    override suspend fun start(port: Int) {
        server = embeddedServer(Netty, port = port) {
            install(WebSockets) {
                pingPeriod = Duration.ofSeconds(15)
                timeout = Duration.ofSeconds(15)
                maxFrameSize = Long.MAX_VALUE
                masking = false
            }
            routing {
                webSocket("/") {
                    connections.add(this)
                    try {
                        handleWebSocketConnection(incoming, outgoing)
                    } finally {
                        connections.remove(this)
                    }
                }


            }
        }.start(wait = false)

        Log.d("ServerTag", "Server started on $port")



        Log.d("Server tag", "Memory monitor started ${monitorMemoryUsage()}")

    }




    override suspend fun stop() {
        server?.stop(1000, 1000)
        job.cancel()
        stopScan()

        Log.d("ServerTag", "Server stopped")

    }



    private suspend fun handleWebSocketConnection(
        incoming: ReceiveChannel<Frame>,
        outgoing: SendChannel<Frame>
    ) {
        Log.d("ServerTag", "New WebSocket connection established")
        try {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        Log.d("ServerTag", "Received WebSocket message: $text")

                        val json = Json.parseToJsonElement(text).jsonObject
                        when (json["action"]?.jsonPrimitive?.content) {
                            "start-scan" -> {
                                val interval = json["interval"]?.jsonPrimitive?.long ?: 60
                                val packageName = json["packageName"]?.jsonPrimitive?.content
                                    ?: "sdcard/Documents"
                                val lastScanJson = json["lastScan"]?.jsonPrimitive?.content
                                val lastScan = if (lastScanJson != null && lastScanJson != "null") {
                                    Json.decodeFromString<FileNode>(lastScanJson)
                                } else {
                                    null
                                }

                                Log.d(
                                    "Server",
                                    "webcokest packageName: $packageName, $lastScanJson, $lastScan"
                                )
                                startScan(interval, packageName, lastScan)
                                outgoing.send(Frame.Text("Scanning started with interval: $interval seconds"))
                            }

                            "stop-scan" -> {
                                stopScan()
                                outgoing.send(Frame.Text("Scanning stopped"))
                            }

                            "get-memory-status" -> {
                                val memoryStatus = monitorMemoryUsage()
                                outgoing.send(Frame.Text(memoryStatus.toString()))
                            }

                            "restore-scan" -> {
                                val scanId = json["scanId"]?.jsonPrimitive?.content
                                if (scanId != null) {
                                    //restoreScan(scanId)
                                    outgoing.send(Frame.Text("Restoration of scan $scanId started"))
                                } else {
                                    outgoing.send(Frame.Text("Error: Scan ID is required"))
                                }
                            }

                        }
                    }

                    is Frame.Binary -> Log.d("ServerTag", "Received non-text frame: $frame")
                    is Frame.Close -> Log.d("ServerTag", "Received non-text frame: $frame")
                    is Frame.Ping -> Log.d("ServerTag", "Received non-text frame: $frame")
                    is Frame.Pong -> Log.d("ServerTag", "Received non-text frame: $frame")
                }
            }
        } catch (e: Exception) {
            Log.e("ServerTag", "Error in WebSocket connection: ${e.message}", e)

        } finally {
            Log.d("ServerTag", "WebSocket connection closed")
        }
    }
    private suspend fun startScan(
        interval: Long,
        packageName: String,
        initialLastScan: FileNode? = null
    ) {
        Log.d("ServerTag", "Starting scan with interval: $interval and package: $packageName")
        Log.d("ServerTag", "Initial lastScan is null: ${initialLastScan == null}")

        stopScan()
        var lastScan = initialLastScan
        scanJob = launch {
            while (isActive) {
                val rootDirectory = File(packageName.trim('"'))
                if (rootDirectory.exists() && rootDirectory.isDirectory) {
                    try {
                        Log.e("ServerTag", "Directory not found or not accessible: ${rootDirectory.absolutePath}")

                        val scanStartTime = Clock.System.now()
                        Log.e("ServerTag", "")
                        val archiveId = scanRepository.getLastId()
                        val fileTree = scanManager.scanDirectory(rootDirectory, lastScan)
                        val scanEndTime = Clock.System.now()
                        val scanDurationMs = scanEndTime.minus(scanStartTime).inWholeMilliseconds

                        if (hasChanges(fileTree, lastScan)) {
                            val scanResult = ScanResult(
                                id = archiveId,
                                fileTree = fileTree,
                                totalSize = calculateTotalSize(fileTree),
                                scanDurationMs = scanDurationMs,
                                scanStartTime = scanStartTime
                            )

                            val jsonString = Json.encodeToString(scanResult)
                            archiveManager.archiveAndSaveScan(scanResult, jsonString)

                            broadcastUpdate(Json.encodeToString(mapOf(
                                "type" to "scan_result",
                                "data" to jsonString
                            )))
                        } else {
                            val scanResult = ScanResult(
                                id = archiveId,
                                fileTree = fileTree,
                                totalSize = calculateTotalSize(fileTree),
                                scanDurationMs = scanDurationMs,
                                scanStartTime = scanStartTime
                            )

                            val jsonString = Json.encodeToString(scanResult)
                            broadcastUpdate(Json.encodeToString(mapOf(
                                "type" to "scan_result",
                                "data" to jsonString
                            )))
                        }
                        lastScan = fileTree
                    } catch (e: Exception) {
                        Log.e("ServerTag", "Error during scanning: ${e.message}", e)
                        broadcastUpdate(Json.encodeToString(mapOf(
                            "type" to "error",
                            "data" to "Scanning error: ${e.message}"
                        )))
                    }
                } else {
                    Log.e("ServerTag", "Directory not found or not accessible: ${rootDirectory.absolutePath}")
                    broadcastUpdate(Json.encodeToString(mapOf(
                        "type" to "error",
                        "data" to "Directory not found or not accessible: ${rootDirectory.absolutePath}"
                    )))
                }
                delay(interval * 1000)
            }
        }
    }





    private fun calculateTotalSize(fileNode: FileNode): Long {
        return fileNode.size + (fileNode.children?.sumOf { calculateTotalSize(it) } ?: 0)
    }

    private suspend fun stopScan() {
        scanJob?.cancel()
        scanJob = null
        Log.d("ServerTag", "Scanning stopped")
    }

    private suspend fun monitorMemoryUsage() {
        memoryManager.getMemoryUsage().collect { memoryUsage ->
            val message = Json.encodeToString(
                mapOf(
                    "used" to memoryUsage.used,
                    "max" to memoryUsage.max
                )
            )
            broadcastUpdate(message)
        }
    }

    private fun compareTrees(current: FileNode, last: FileNode): Boolean {
        val status = when {
            last == null -> FileStatus.NEW
            current.name != last.name || current.size != last.size -> FileStatus.MODIFIED
            else -> FileStatus.UNCHANGED
        }

        current.status = status

        if (status != FileStatus.UNCHANGED) return false

        if (current.isDirectory) {
            if (current.children.size != last.children.size) return false
            return current.children.zip(last.children).all { (c, l) -> compareTrees(c, l) }
        }

        return true
    }

    private fun hasChanges(currentScan: FileNode, lastScan: FileNode?): Boolean {
        return if (lastScan == null) {
            markTreeAsNew(currentScan)
            true
        } else {
            !compareTrees(currentScan, lastScan)
        }
    }

    private fun markTreeAsNew(node: FileNode) {
        node.status = FileStatus.NEW
        node.children.forEach { markTreeAsNew(it) }
    }

    private suspend fun broadcastUpdate(message: String) {
        connections.forEach { socket ->
            socket.send(Frame.Text(message))
        }
    }

}