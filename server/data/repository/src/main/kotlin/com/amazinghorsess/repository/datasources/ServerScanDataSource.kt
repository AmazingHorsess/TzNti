package com.amazinghorsess.repository.datasources

import com.amazinghorsess.repository.model.ScanResult

interface ServerScanDataSource {
    suspend fun saveScan(scanResult: ScanResult, txtFilePath: String, archivePath: String)
    suspend fun getScanById(id: Int): ScanResult?
    suspend fun getAllScans(): List<ScanResult>
    suspend fun getLastId(): Int
}