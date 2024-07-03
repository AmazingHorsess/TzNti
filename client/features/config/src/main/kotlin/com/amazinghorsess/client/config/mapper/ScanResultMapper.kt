package com.amazinghorsess.client.config.mapper

import com.amazinghorsess.client.config.model.ScanResult as ViewScanResult
import com.amazinghorsess.client.domain.model.ScanResult as DomainScanResult

internal class ScanResultMapper(
    private val fileNodeMapper: FileNodeMapper
) {
    fun toView(domainScanResult: DomainScanResult): ViewScanResult{
        return ViewScanResult(
            id = domainScanResult.id,
            scanDurationMs = domainScanResult.scanDurationMs,
            totalSize = domainScanResult.totalSize,
            scanStartTime = domainScanResult.scanStartTime,
            fileTree = domainScanResult.fileTree?.let { fileNodeMapper.toView(it) }
        )
    }
}