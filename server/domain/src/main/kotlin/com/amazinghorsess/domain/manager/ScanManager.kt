package com.amazinghorsess.domain.manager

import com.amazinghorsess.domain.model.FileNode
import java.io.File

interface ScanManager {

    suspend fun scanDirectory(directory: File,lastScan: FileNode?): FileNode



}