package com.amazinghorsess.repository.mapper
import com.amazinghorsess.domain.model.FileNode
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.amazinghorsess.repository.model.ScanResult as RepositoryScanResult
import com.amazinghorsess.domain.model.ScanResult as DomainScanResult

internal class ScanResultMapper {
    fun toRepository(domainScanResult: DomainScanResult, txtFilePath: String, archivePath: String): RepositoryScanResult {
        return RepositoryScanResult(
            id = domainScanResult.id,
            totalSize = domainScanResult.totalSize ?: 0,
            scanDurationMs = domainScanResult.scanDurationMs,
            scanStartTime = domainScanResult.scanStartTime?.toString() ?: "",
            txtFilePath = txtFilePath,
            archivePath = archivePath,
            fileTreeJson = Json.encodeToString(domainScanResult.fileTree)

        )
    }

    fun toDomain(repositoryScanResult: RepositoryScanResult): DomainScanResult {
        return DomainScanResult(
            id = repositoryScanResult.id,
            fileTree = Json.decodeFromString<FileNode?>(repositoryScanResult.fileTreeJson),
            totalSize = repositoryScanResult.totalSize,
            scanDurationMs = repositoryScanResult.scanDurationMs,
            scanStartTime = if (repositoryScanResult.scanStartTime.isNotEmpty()) Instant.parse(repositoryScanResult.scanStartTime) else null
        )
    }


}