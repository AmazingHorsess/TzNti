package com.amazinghorsess.client.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class MemoryUsage(
    val used:Long,
    val max: Long,
)