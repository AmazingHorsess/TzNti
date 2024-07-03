package com.amazinghorsess.network.ktor.websockets

import io.ktor.websocket.WebSocketSession

interface ApiService {
    suspend fun connectToServer(url: String): WebSocketSession
}
