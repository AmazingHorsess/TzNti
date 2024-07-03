package com.amazinghorsess.archiving

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.amazinghorsess.domain.manager.ArchiveManager
import com.amazinghorsess.domain.model.FileNode
import com.amazinghorsess.domain.model.ScanResult
import com.amazinghorsess.domain.repository.ServerScanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

internal class ArchiveManagerImpl(
    private val context: Context,
    private val serverScanRepository: ServerScanRepository,
): ArchiveManager {


    override suspend fun archiveAndSaveScan(scanResult: ScanResult,jsonString: String) {
        val archiveId = serverScanRepository.getLastId()

        val zipFileName = "scan_archive_$archiveId.zip"
        val txtFileName = "scanresult_${scanResult.scanStartTime}.txt"
        val archiveFile = createArchiveFile(scanResult.id)
        val txtFile = createTxtFile(scanResult)

        withContext(Dispatchers.IO) {
            ZipOutputStream(FileOutputStream(archiveFile)).use { zipOut ->
                zipFileStructure(scanResult.fileTree, zipOut)
            }
        }
        val outputDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ?: context.filesDir
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val zipFile = File(outputDir, zipFileName)

        createZipWithJson(jsonString, zipFile, txtFileName)

        serverScanRepository.saveScan(
            scanResult = scanResult,
            txtFilePath = txtFile.absolutePath,
            archivePath = archiveFile.absolutePath
        )
    }

    override suspend fun getScanById(id: Int): ScanResult? {
        return serverScanRepository.getScanById(id)
    }

    override suspend fun getAllScans(): List<ScanResult> {
        return serverScanRepository.getAllScans()
    }

    private fun createArchiveFile(scanId: Int): File {
        val archiveDir = File(context.filesDir, "archives")
        if (!archiveDir.exists()) {
            archiveDir.mkdirs()
        }
        return File(archiveDir, "scan_$scanId.zip")
    }

    private fun createTxtFile(scanResult: ScanResult): File {
        val txtDir = File(context.filesDir, "txt_files")
        if (!txtDir.exists()) {
            txtDir.mkdirs()
        }
        val txtFile = File(txtDir, "scan_${scanResult.id}.txt")
        Log.d("ArchiveManagerImpl", "createTxtFile: ${Json.encodeToString(scanResult)}")
        Log.d("ArchiveManagerImpl", "text file: $txtFile")
        txtFile.writeText(Json.encodeToString(scanResult))
        return txtFile
    }
    private fun createZipWithJson(jsonString: String, zipFile: File, txtFileName: String) {
        ZipOutputStream(FileOutputStream(zipFile)).use { zipOut ->
            val zipEntry = ZipEntry(txtFileName)
            zipOut.putNextEntry(zipEntry)
            zipOut.write(jsonString.toByteArray())
            zipOut.closeEntry()
        }
    }


    private fun zipFileStructure(fileNode: FileNode?, zipOut: ZipOutputStream, path: String = "") {
        fileNode ?: return

        val newPath = if (path.isEmpty()) fileNode.name else "$path/${fileNode.name}"

        if (fileNode.isDirectory) {
            fileNode.children.forEach { child ->
                zipFileStructure(child, zipOut, newPath)
            }
        } else {
            zipOut.putNextEntry(java.util.zip.ZipEntry(newPath))
            // Here you would write the actual file content
            // For this example, we're just writing the file size as content
            zipOut.write(fileNode.size.toString().toByteArray())
            zipOut.closeEntry()
        }
    }
}

