package com.amazinghorsess.client.config

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazinghorsess.client.config.mapper.FileNodeMapper
import com.amazinghorsess.client.config.mapper.MemoryUsageMapper
import com.amazinghorsess.client.config.mapper.ScanResultMapper
import com.amazinghorsess.client.config.model.FileNode
import com.amazinghorsess.client.config.model.MemoryUsage
import com.amazinghorsess.client.config.model.ScanResult
import com.amazinghorsess.client.domain.repository.ServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ConfigViewModel(
    private val serverRepository: ServerRepository,
    private val fileNodeMapper: FileNodeMapper,
    private val memoryUsageMapper: MemoryUsageMapper,
    private val scanResultMapper: ScanResultMapper,
) : ViewModel() {

    private val _memoryUsage = MutableStateFlow<MemoryUsage?>(null)
    val memoryUsage: StateFlow<MemoryUsage?> = _memoryUsage.asStateFlow()

    private val _scanResult = MutableStateFlow<ScanResult?>(null)
    val scanResult: StateFlow<ScanResult?> = _scanResult.asStateFlow()

    private val _connectionStatus = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    fun connectToServer(url: String) {
        viewModelScope.launch {
            try {
                _connectionStatus.value = ConnectionStatus.Connecting
                serverRepository.connect(url)
                _connectionStatus.value = ConnectionStatus.Connected
                observeServerMessages()
            } catch (e: Exception) {
                _connectionStatus.value = ConnectionStatus.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun startScan(interval: Long, packageName: String, lastFile: FileNode?) {
        Log.d("ConfigViewModel", "Calling startScan function")
        viewModelScope.launch {
            try {
                serverRepository.startScan(
                    interval,
                    packageName,
                    lastFile?.let { fileNodeMapper.toDomain(it) }
                )
            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error starting scan", e)
            }
        }
    }

    fun stopScan() {
        viewModelScope.launch {
            try {
                serverRepository.stopScan()
            } catch (e: Exception) {
                Log.e("ConfigViewModel", "Error stopping scan", e)
            }
        }
    }

    private fun observeServerMessages() {
        viewModelScope.launch {
            serverRepository.getMemoryStatus().collect { memoryUsage ->
                _memoryUsage.value = memoryUsageMapper.toView(memoryUsage)
            }
        }

        viewModelScope.launch {
            serverRepository.getScanResult().collect { scanResult ->
                _scanResult.value = scanResultMapper.toView(scanResult)
            }
        }
    }
}

sealed class ConnectionStatus {
    object Disconnected : ConnectionStatus()
    object Connecting : ConnectionStatus()
    object Connected : ConnectionStatus()
    data class Error(val message: String) : ConnectionStatus()
}