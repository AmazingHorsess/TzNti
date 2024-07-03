package com.amazinghorsess.scanlist.model

import kotlinx.datetime.Instant

data class ScanResult(
    val id: Int,
    val totalSize: Long? = null,
    val scanDurationMs: Long = 0,
    val scanStartTime: Instant? = null,
)