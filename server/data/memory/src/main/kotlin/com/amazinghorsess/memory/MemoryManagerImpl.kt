package com.amazinghorsess.memory

import com.amazinghorsess.domain.manager.MemoryManager
import com.amazinghorsess.domain.model.MemoryUsage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class MemoryManagerImpl: MemoryManager {

    override fun getMemoryUsage(): Flow<MemoryUsage> = flow {
        while (true) {
            val usedMemory = getCurrentMemoryUsage()
            val maxMemory = getMaxMemory()

            emit(MemoryUsage(usedMemory, maxMemory))

            delay(4000)
        }

    }

    private fun getCurrentMemoryUsage(): Long {

        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    }

    private fun getMaxMemory(): Long {

        return Runtime.getRuntime().maxMemory()
    }
}