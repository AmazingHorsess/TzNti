package com.amazinghorsess.scanlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazinghorsess.client.domain.repository.ServerRepository
import com.amazinghorsess.scanlist.mapper.ScanResultMapper
import com.amazinghorsess.scanlist.model.ScanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ScanListViewModel(
    private val serverRepository: ServerRepository,
    private val scanResultMapper: ScanResultMapper
) : ViewModel() {
    private val _scanHistory = MutableStateFlow<List<ScanResult>>(emptyList())
    val scanHistory: StateFlow<List<ScanResult>> = _scanHistory.asStateFlow()

    private val _currentScanResult = MutableStateFlow<ScanResult?>(null)
    val currentScanResult: StateFlow<ScanResult?> = _currentScanResult.asStateFlow()

    init {
        observeScanResults()
    }

    private fun observeScanResults() {
        viewModelScope.launch {
            serverRepository.getScanResult().collect { repositoryScanResult ->
                val viewScanResult = scanResultMapper.toView(repositoryScanResult)
                _currentScanResult.value = viewScanResult
                addToHistory(viewScanResult)
            }
        }
    }

    private fun addToHistory(scanResult: ScanResult) {
        _scanHistory.update { currentList ->
            (currentList + scanResult)
        }
    }

    fun loadScan(scanId: Int) {
        viewModelScope.launch {
            val scan = _scanHistory.value.find { it.id == scanId }
            _currentScanResult.value = scan
        }
    }
}