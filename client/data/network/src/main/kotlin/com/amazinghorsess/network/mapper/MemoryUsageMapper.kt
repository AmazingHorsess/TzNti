package com.amazinghorsess.network.mapper

import com.amazinghorsess.network.model.MemoryUsage as NetworkMemoryUsage
import com.amazinghorsess.client.repository.model.MemoryUsage as RepositoryMemoryUsage

internal class MemoryUsageMapper {
    fun toRepository(networkMemoryUsage: NetworkMemoryUsage) : RepositoryMemoryUsage{
        return RepositoryMemoryUsage(
            networkMemoryUsage.used,
            networkMemoryUsage.max,
        )
    }
}