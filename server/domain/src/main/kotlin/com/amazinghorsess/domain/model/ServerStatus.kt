package com.amazinghorsess.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class ServerStatus {
    RUNNING,
    STOPPED
}