package com.amazinghorsess.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MemoryUsage(
    val used: Long,
    val max: Long,
)