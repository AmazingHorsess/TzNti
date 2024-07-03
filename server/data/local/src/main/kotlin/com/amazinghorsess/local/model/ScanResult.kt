package com.amazinghorsess.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_results")
data class ScanResult(
    @PrimaryKey(autoGenerate = true)   val id: Int,
    val totalSize: Long,
    val scanDurationMs: Long,
    val scanStartTime: String,
    val txtFilePath: String,
    val archivePath: String,
    val fileTreeJson: String
)