package com.amazinghorsess.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ScanResult(
    val id: Int,
    val fileTree: FileNode,
    val totalSize: Long,
    val scanDurationMs: Long,
    val scanStartTime: String
)