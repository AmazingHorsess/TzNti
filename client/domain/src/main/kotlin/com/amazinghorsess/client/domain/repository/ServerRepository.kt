package com.amazinghorsess.client.domain.repository

import com.amazinghorsess.client.domain.model.FileNode
import com.amazinghorsess.client.domain.model.MemoryUsage
import com.amazinghorsess.client.domain.model.ScanResult
import com.amazinghorsess.client.domain.model.ServerStatus
import kotlinx.coroutines.flow.Flow

interface ServerRepository {
    suspend fun connect(url: String)
    suspend fun startScan(interval: Long, packageName: String,lastFile:FileNode?)
    suspend fun stopScan()
    suspend fun getMemoryStatus(): Flow<MemoryUsage>
    suspend fun getScanResult(): Flow<ScanResult>
}