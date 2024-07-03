package com.amazinghorsess.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazinghorsess.domain.manager.ScanManager
import com.amazinghorsess.domain.model.FileNode
import com.amazinghorsess.domain.model.ScanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class FileViewModel(
    private val scanManager: ScanManager
): ViewModel(){
    private val _scanResult = MutableStateFlow<ScanResult?>(null)
    val scanResult: StateFlow<ScanResult?> = _scanResult.asStateFlow()

    fun startScanning(directory: File, lastScan: FileNode?) {
        viewModelScope.launch {
            try {
                val startTime = System.currentTimeMillis()
                val fileTree = scanManager.scanDirectory(directory, lastScan)
                val endTime = System.currentTimeMillis()
                Log.d("FileViewModel", "$directory")

                val newScanResult = ScanResult(
                    fileTree = fileTree,
                    totalSize = calculateTotalSize(fileTree),
                    scanDurationMs = endTime - startTime,
                    scanStartTime = Clock.System.now()
                )

                _scanResult.value = newScanResult
                Log.d("FileViewModel", "Scan result $newScanResult")

                val jsonString = Json.encodeToString(newScanResult)
                println(jsonString)
                // archiveAndSaveResult(newScanResult)
            } catch (e: Exception) {
                // Обработка ошибок
                Log.e("FileViewModel", "Error during scanning", e)
            }
        }
    }

    private fun calculateTotalSize(fileNode: FileNode): Long {
        return if (fileNode.isDirectory) {
            fileNode.children.sumOf { calculateTotalSize(it) }
        } else {
            fileNode.size
        }
    }

}