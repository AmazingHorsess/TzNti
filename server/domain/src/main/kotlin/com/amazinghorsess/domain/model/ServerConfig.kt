package com.amazinghorsess.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerConfig(
    val port: Int,
)
