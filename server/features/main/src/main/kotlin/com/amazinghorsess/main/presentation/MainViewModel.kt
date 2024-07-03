package com.amazinghorsess.main.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazinghorsess.domain.interactor.ServerInteractor
import com.amazinghorsess.domain.manager.MemoryManager
import com.amazinghorsess.main.mapper.MemoryUsageMapper
import com.amazinghorsess.main.mapper.ServerStatusMapper
import com.amazinghorsess.main.model.MemoryUsage
import com.amazinghorsess.main.model.ServerStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val serverInteractor: ServerInteractor,
    private val serverStatusMapper: ServerStatusMapper,
    private val memoryUsageMapper: MemoryUsageMapper,
    private val memoryManager: MemoryManager,


): ViewModel() {




    private val _memoryUsage = MutableStateFlow(MemoryUsage(0, 0))
    val memoryUsage: StateFlow<MemoryUsage?> = _memoryUsage.asStateFlow()



    fun startServer(port: Int = 8080) {
        viewModelScope.launch(Dispatchers.IO) {
            serverInteractor.start(port)
        }
        observeMemoryUsage()

    }

    fun stopServer() {
        viewModelScope.launch(Dispatchers.IO) {
            serverInteractor.stop()
        }
    }



    private fun observeMemoryUsage() {
        viewModelScope.launch {
            memoryManager.getMemoryUsage().collect { usage ->
                val mappedUsage = memoryUsageMapper.toView(usage)
                _memoryUsage.value = mappedUsage
            }
        }
    }
}








