package com.amazinghorsess.domain.manager

import com.amazinghorsess.domain.model.ScanResult

interface ArchiveManager {
    suspend fun archiveAndSaveScan(scanResult: ScanResult,jsonString: String)
    suspend fun getScanById(id: Int): ScanResult?
    suspend fun getAllScans():List<ScanResult>
}