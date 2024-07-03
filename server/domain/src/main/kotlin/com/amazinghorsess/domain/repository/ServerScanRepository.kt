package com.amazinghorsess.domain.repository

import com.amazinghorsess.domain.model.ScanResult
import kotlinx.coroutines.flow.Flow
interface ServerScanRepository {

    suspend fun saveScan(scanResult: ScanResult, txtFilePath: String, archivePath: String)
    suspend fun getScanById(id: Int): ScanResult?
    suspend fun getAllScans(): List<ScanResult>
    suspend fun getLastId(): Int


}