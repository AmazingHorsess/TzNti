package com.amazinghorsess.client.repository.datasources


import com.amazinghorsess.client.repository.model.FileNode
import com.amazinghorsess.client.repository.model.MemoryUsage
import com.amazinghorsess.client.repository.model.ScanResult
import kotlinx.coroutines.flow.Flow

interface ServerDataSource {
    suspend fun connect(url: String)
    suspend fun startScan(interval: Long, packageName: String,lastFile: FileNode?)
    suspend fun stopScan()
    suspend fun getMemoryStatus(): Flow<MemoryUsage>
    suspend fun getScanResult(): Flow<ScanResult>
}