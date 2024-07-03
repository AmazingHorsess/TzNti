package com.amazinghorsess.network.ktor.websockets

import com.amazinghorsess.network.model.MemoryUsage
import com.amazinghorsess.network.model.ScanResult

sealed class WebSocketMessage {
    data class MemoryUsageMessage(val data: MemoryUsage) : WebSocketMessage()
    data class ScanResultMessage(val data: String) : WebSocketMessage()
}