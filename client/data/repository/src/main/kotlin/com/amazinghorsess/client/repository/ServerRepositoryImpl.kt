package com.amazinghorsess.client.repository


import com.amazinghorsess.client.domain.model.FileNode
import com.amazinghorsess.client.domain.model.MemoryUsage
import com.amazinghorsess.client.domain.model.ScanResult
import com.amazinghorsess.client.domain.repository.ServerRepository
import com.amazinghorsess.client.repository.datasources.ServerDataSource
import com.amazinghorsess.client.repository.mapper.FileNodeMapper
import com.amazinghorsess.client.repository.mapper.MemoryUsageMapper
import com.amazinghorsess.client.repository.mapper.ScanResultMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ServerRepositoryImpl(
    private val memoryUsageMapper: MemoryUsageMapper,
    private val dataSource: ServerDataSource,
    private val fileNodeMapper: FileNodeMapper,
    private val scanResultMapper: ScanResultMapper
) : ServerRepository {
    override suspend fun connect(url: String) {
        dataSource.connect(url)
    }

    override suspend fun startScan(interval: Long, packageName: String, lastFile: FileNode?) {
        dataSource.startScan(
            interval,
            packageName,
            lastFile?.let { fileNodeMapper.toRepository(it) })
    }

    override suspend fun stopScan() {
        dataSource.stopScan()
    }

    override suspend fun getMemoryStatus(): Flow<MemoryUsage> = flow {
        dataSource.getMemoryStatus().collect { memoryUsage ->
            emit(memoryUsageMapper.toDomain(memoryUsage))

        }
    }
    override suspend fun getScanResult(): Flow<ScanResult> = flow {
        dataSource.getScanResult().collect{
            emit(scanResultMapper.toDomain(it))
        }
    }
}



