package com.amazinghorsess.repository

import com.amazinghorsess.domain.model.ScanResult
import com.amazinghorsess.domain.repository.ServerScanRepository
import com.amazinghorsess.repository.datasources.ServerScanDataSource
import com.amazinghorsess.repository.mapper.ScanResultMapper

internal class ServerScanRepositoryImpl(
    private val dataSource: ServerScanDataSource,
    private val scanResultMapper: ScanResultMapper
): ServerScanRepository {
    override suspend fun saveScan(
        scanResult: ScanResult,
        txtFilePath: String,
        archivePath: String
    ) = dataSource.saveScan(scanResultMapper.toRepository(scanResult,txtFilePath,archivePath),txtFilePath,archivePath)


    override suspend fun getScanById(id: Int): ScanResult? =
        dataSource.getScanById(id)?.let { scanResultMapper.toDomain(it) }

    override suspend fun getAllScans(): List<ScanResult> =
        dataSource.getAllScans().map { scanResultMapper.toDomain(it) }

    override suspend fun getLastId(): Int =
        dataSource.getLastId()




}