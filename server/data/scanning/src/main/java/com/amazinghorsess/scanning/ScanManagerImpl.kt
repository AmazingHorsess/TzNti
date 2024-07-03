package com.amazinghorsess.scanning

import android.content.Context
import android.os.Environment
import android.util.Log
import com.amazinghorsess.domain.manager.ArchiveManager
import com.amazinghorsess.domain.manager.ScanManager
import com.amazinghorsess.domain.model.FileNode
import com.amazinghorsess.domain.model.FileStatus
import com.amazinghorsess.domain.model.ScanResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

internal class ScanManagerImpl(

): ScanManager{

    override suspend fun scanDirectory(
        directory: File,
        lastScan: FileNode?
    ): FileNode = withContext(Dispatchers.IO){
        return@withContext scanDirectoryRecursive(directory, lastScan)
        }




    private fun  scanDirectoryRecursive(file:File, lastScan: FileNode?): FileNode{
        val children = file.listFiles()?.map { childFile ->
            scanDirectoryRecursive(childFile, lastScan?.children?.find {
                it.name == childFile.name
                }
            )
        } ?: emptyList()


        val status = when {
            lastScan == null -> FileStatus.NEW
            file.isDirectory -> FileStatus.UNCHANGED
            lastScan.size != file.length() -> FileStatus.MODIFIED
            else -> FileStatus.UNCHANGED
        }

        return FileNode(
            name = file.name,
            isDirectory = file.isDirectory,
            size = if (file.isDirectory) children.sumOf { it.size } else file.length(),
            status = status,
            children = children
        )
    }






}