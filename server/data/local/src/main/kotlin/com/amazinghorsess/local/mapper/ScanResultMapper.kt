package com.amazinghorsess.local.mapper

import com.amazinghorsess.local.model.ScanResult as LocalScanResult
import com.amazinghorsess.repository.model.ScanResult as RepositoryScanResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ScanResultMapper {
    fun toLocal(repository: RepositoryScanResult): LocalScanResult {
        return LocalScanResult(
            id = repository.id,
            totalSize = repository.totalSize,
            scanDurationMs = repository.scanDurationMs,
            scanStartTime = repository.scanStartTime,
            txtFilePath = repository.txtFilePath,
            archivePath = repository.archivePath,
            fileTreeJson = repository.fileTreeJson
        )
    }

    fun toRepository(local: LocalScanResult): RepositoryScanResult {
        return RepositoryScanResult(
            id = local.id,
            totalSize = local.totalSize,
            scanDurationMs = local.scanDurationMs,
            scanStartTime = local.scanStartTime,
            txtFilePath = local.txtFilePath,
            archivePath = local.archivePath,
            fileTreeJson = local.fileTreeJson
        )
    }

    fun toLocal(repository: RepositoryScanResult, txtFilePath: String, archivePath: String): LocalScanResult {
        return LocalScanResult(
            id = 0,
            totalSize = repository.totalSize,
            scanDurationMs = repository.scanDurationMs,
            scanStartTime = repository.scanStartTime,
            txtFilePath = txtFilePath,
            archivePath = archivePath,
            fileTreeJson = repository.fileTreeJson
        )
    }

    fun toRepositoryWithDeserializedFileTree(local: LocalScanResult): RepositoryScanResult {
        return RepositoryScanResult(
            id = local.id,
            totalSize = local.totalSize,
            scanDurationMs = local.scanDurationMs,
            scanStartTime = local.scanStartTime,
            txtFilePath = local.txtFilePath,
            archivePath = local.archivePath,
            fileTreeJson = local.fileTreeJson
        )
    }
}