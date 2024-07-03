package com.amazinghorsess.network.ktor.websockets

import android.util.Log
import com.amazinghorsess.network.model.FileNode

import com.amazinghorsess.network.model.MemoryUsage
import com.amazinghorsess.network.model.ScanResult
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class WebSocketClient(private val apiService: ApiService) {

    private var webSocket: WebSocketSession? = null

    suspend fun connect(url: String) {
        webSocket = apiService.connectToServer(url)
    }


    suspend fun startScan(interval: Long, packageName: String, lastFile: FileNode?) {
        Log.d(
            "ServerRepository",
            "Preparing to start scan: interval=$interval, packageName=$packageName, lastScan=$lastFile"
        )
        val jsonLastFile = lastFile?.let { Json.encodeToString(it) }

        val message = Json.encodeToString(
            mapOf(
                "action" to "start-scan",
                "interval" to interval,
                "packageName" to packageName,
                "lastScan" to jsonLastFile
            )
        )
        Log.d("ServerRepository", "Scan JSON message: $message")

        try {
            webSocket?.send(Frame.Text(message))
            Log.d("ServerRepository", "Start-scan message sent successfully")

        } catch (e: Exception) {
            Log.e("ServerRepository", "Error sending start-scan message", e)
        }
    }

    suspend fun stopScan() {
        webSocket?.send(Frame.Text(Json.encodeToString(mapOf("action" to "stop-scan"))))
    }

    suspend fun getScanResult(): Flow<ScanResult> = flow {
        webSocket?.send(Frame.Text(Json.encodeToString(mapOf("action" to "scan-result"))))
        for (frame in webSocket?.incoming ?: return@flow) {
            if (frame is Frame.Text) {
                val json = frame.readText()
                try {
                    val scanResult = Json.decodeFromString<ScanResult>(json)
                    emit(scanResult)
                } catch (e: SerializationException) {
                    Log.d("WebSocketClient", "Received non-ScanResult message: $json")
                }
            }
        }
    }

    suspend fun getMemoryStatus(): Flow<MemoryUsage> = flow {
        webSocket?.send(Frame.Text(Json.encodeToString(mapOf("action" to "get-memory-status"))))
        for (frame in webSocket?.incoming ?: return@flow) {
            if (frame is Frame.Text) {
                val json = frame.readText()
                try {
                    val memoryUsage = Json.decodeFromString<MemoryUsage>(json)
                    emit(memoryUsage)
                } catch (e: SerializationException) {
                    Log.d("WebSocketClient", "Received non-MemoryUsage message: $json")
                }
            }
        }
    }

    suspend fun handleIncomingMessages(): Flow<WebSocketMessage> = flow {
        for (frame in webSocket?.incoming ?: return@flow) {
            if (frame is Frame.Text) {
                val json = frame.readText()
                try {
                    val jsonObject = Json.parseToJsonElement(json).jsonObject
                    when {
                        jsonObject.containsKey("used") && jsonObject.containsKey("max") -> {
                            val memoryUsage = Json.decodeFromJsonElement<MemoryUsage>(jsonObject)
                            emit(WebSocketMessage.MemoryUsageMessage(memoryUsage))
                        }
                        jsonObject.containsKey("type") && jsonObject["type"]?.jsonPrimitive?.content == "scan_result" -> {
                            val scanResultData = jsonObject["data"]?.jsonPrimitive?.content
                            if (scanResultData != null) {
                                emit(WebSocketMessage.ScanResultMessage(scanResultData))
                            }
                        }
                        else -> {
                            Log.d("WebSocketClient", "Received unknown message: $json")
                        }
                    }
                } catch (e: SerializationException) {
                    Log.e("WebSocketClient", "Error parsing message: $json", e)
                }
            }
        }
    }
}