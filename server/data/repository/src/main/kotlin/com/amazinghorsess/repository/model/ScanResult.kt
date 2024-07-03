package com.amazinghorsess.repository.model

import com.amazinghorsess.domain.model.FileNode
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ScanResult(
    val id: Int,
    val totalSize: Long,
    val scanDurationMs: Long,
    val scanStartTime: String,
    val txtFilePath: String,
    val archivePath: String,
    val fileTreeJson: String
)

