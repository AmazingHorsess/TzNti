package com.amazinghorsess.local.datasource

import com.amazinghorsess.local.dao.ServerScanDao
import com.amazinghorsess.local.mapper.ScanResultMapper
import com.amazinghorsess.local.provider.DaoProvider
import com.amazinghorsess.repository.datasources.ServerScanDataSource
import com.amazinghorsess.repository.model.ScanResult

internal class ServerScanLocalDataSource(
    daoProvider: DaoProvider,
    private val scanResultMapper: ScanResultMapper
): ServerScanDataSource {
    private val scanResultDao = daoProvider.getServerScanDao()
    override suspend fun saveScan(
        scanResult: ScanResult,
        txtFilePath: String,
        archivePath: String
    ) {
       scanResultDao.insert(scanResultMapper.toLocal(scanResult,txtFilePath, archivePath))
    }

    override suspend fun getScanById(id: Int): ScanResult? =
        scanResultDao.getScanById(id)?.let { scanResultMapper.toRepository(it) }

    override suspend fun getAllScans(): List<ScanResult> =
        scanResultDao.getAllScans().map { scanResultMapper.toRepository(it) }

    override suspend fun getLastId(): Int {
        val maxId = scanResultDao.getMaxId()
        return (maxId ?:0) + 1
    }
}