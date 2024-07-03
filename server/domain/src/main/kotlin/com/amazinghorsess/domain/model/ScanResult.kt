package com.amazinghorsess.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ScanResult(
    val id: Int = 0,
    val fileTree: FileNode? = null,
    val totalSize: Long? = null,
    val scanDurationMs: Long = 0,
    val scanStartTime: Instant? = null,
)