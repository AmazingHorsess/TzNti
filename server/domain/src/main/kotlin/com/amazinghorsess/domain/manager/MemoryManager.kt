package com.amazinghorsess.domain.manager

import com.amazinghorsess.domain.model.MemoryUsage
import kotlinx.coroutines.flow.Flow

interface MemoryManager {
    fun getMemoryUsage(): Flow<MemoryUsage>
}
