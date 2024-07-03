package com.amazinghorsess.network.ktor.websockets

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.WebSocketSession

class ApiServiceImpl : ApiService {
    private val client = HttpClient(CIO) {
        install(WebSockets)
    }

    override suspend fun connectToServer(url: String): WebSocketSession {
        return client.webSocketSession(urlString = url)
    }
}