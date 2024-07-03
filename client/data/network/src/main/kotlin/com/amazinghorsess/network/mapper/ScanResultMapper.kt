package com.amazinghorsess.network.mapper

import kotlinx.datetime.Instant
import java.time.format.DateTimeParseException
import com.amazinghorsess.client.repository.model.ScanResult as RepositoryScanResult
import com.amazinghorsess.network.model.ScanResult as NetworkScanResult

internal class ScanResultMapper(
    private val fileNodeMapper: FileNodeMapper
){
    fun toRepository(networkScanResult: NetworkScanResult): RepositoryScanResult {
        return RepositoryScanResult(
            id = networkScanResult.id,
            fileTree = networkScanResult.fileTree?.let { fileNodeMapper.toRepository(it) },
            totalSize = networkScanResult.totalSize,
            scanDurationMs = networkScanResult.scanDurationMs,
            scanStartTime = networkScanResult.scanStartTime.toInstantOrNull()
        )
    }

    fun toNetwork(repositoryScanResult: RepositoryScanResult): NetworkScanResult {
        return NetworkScanResult(
            id = repositoryScanResult.id,
            fileTree = repositoryScanResult.fileTree?.let { fileNodeMapper.toNetwork(it) }
                ?: throw IllegalArgumentException("FileTree cannot be null for network model"),
            totalSize = repositoryScanResult.totalSize ?: 0L,
            scanDurationMs = repositoryScanResult.scanDurationMs,
            scanStartTime = repositoryScanResult.scanStartTime?.toString() ?: ""
        )
    }

    private fun String.toInstantOrNull(): Instant? {
        return try {
            Instant.parse(this)
        } catch (e: DateTimeParseException) {
            null
        }
    }

}