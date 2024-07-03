package com.amazinghorsess.scanlist.mapper

import com.amazinghorsess.scanlist.model.ScanResult as ViewScanResult
import com.amazinghorsess.client.domain.model.ScanResult as DomainScanResult

internal class ScanResultMapper{

    fun toView(domainScanResult: DomainScanResult): ViewScanResult{
        return ViewScanResult(
            id = domainScanResult.id,
            scanStartTime = domainScanResult.scanStartTime,
            totalSize = domainScanResult.totalSize,
            scanDurationMs = domainScanResult.scanDurationMs
        )
    }

}