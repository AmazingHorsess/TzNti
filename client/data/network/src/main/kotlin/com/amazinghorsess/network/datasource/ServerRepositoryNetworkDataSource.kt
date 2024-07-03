package com.amazinghorsess.network.datasource

import com.amazinghorsess.client.repository.datasources.ServerDataSource
import com.amazinghorsess.client.repository.model.FileNode
import com.amazinghorsess.client.repository.model.ScanResult
import com.amazinghorsess.network.ktor.websockets.WebSocketClient
import com.amazinghorsess.network.ktor.websockets.WebSocketMessage
import com.amazinghorsess.network.mapper.FileNodeMapper
import com.amazinghorsess.network.mapper.MemoryUsageMapper
import com.amazinghorsess.network.mapper.ScanResultMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

internal class ServerRepositoryNetworkDataSource(
    private val webSocketClient: WebSocketClient,
    private val fileNodeMapper: FileNodeMapper,
    private val memoryUsageMapper: MemoryUsageMapper,
    private val scanResultMapper: ScanResultMapper

): ServerDataSource{
    private val messageFlow = MutableSharedFlow<WebSocketMessage>()


    override suspend fun connect(url: String) {
        webSocketClient.connect(url)
        CoroutineScope(Dispatchers.IO).launch {
            webSocketClient.handleIncomingMessages().collect { message ->
                messageFlow.emit(message)
            }
        }
    }

    override suspend fun startScan(interval: Long, packageName: String, lastFile: FileNode?) {
        webSocketClient.startScan(interval,packageName,
            lastFile?.let { fileNodeMapper.toNetwork(it) })
    }

    override suspend fun stopScan() {
        webSocketClient.stopScan()
    }

    override suspend fun getMemoryStatus(): Flow<com.amazinghorsess.client.repository.model.MemoryUsage> = messageFlow
        .filterIsInstance<WebSocketMessage.MemoryUsageMessage>()
        .map { memoryUsageMapper.toRepository(it.data) }

    override suspend fun getScanResult(): Flow<ScanResult> = messageFlow
        .filterIsInstance<WebSocketMessage.ScanResultMessage>()
        .map { message ->
            val jsonObject = Json.parseToJsonElement(message.data).jsonObject
            val networkScanResult = Json.decodeFromJsonElement<com.amazinghorsess.network.model.ScanResult>(jsonObject)
            scanResultMapper.toRepository(networkScanResult)
        }
}