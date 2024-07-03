package com.amazinghorsess.client.repository.mapper

import com.amazinghorsess.client.domain.model.ScanResult as DomainScanResult
import com.amazinghorsess.client.repository.model.ScanResult as RepositoryScanResult

internal class ScanResultMapper(
    private val fileNodeMapper: FileNodeMapper
){

    fun toDomain(repositoryScanResult: RepositoryScanResult): DomainScanResult{
        return DomainScanResult(
            id = repositoryScanResult.id,
            scanDurationMs = repositoryScanResult.scanDurationMs,
            totalSize = repositoryScanResult.totalSize,
            scanStartTime = repositoryScanResult.scanStartTime,
            fileTree = repositoryScanResult.fileTree?.let { fileNodeMapper.toDomain(it) }
        )
    }
}