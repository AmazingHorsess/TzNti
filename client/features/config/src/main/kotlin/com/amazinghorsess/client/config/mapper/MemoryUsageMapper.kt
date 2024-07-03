package com.amazinghorsess.client.config.mapper

import com.amazinghorsess.client.config.model.MemoryUsage as ViewMemoryUsage
import com.amazinghorsess.client.domain.model.MemoryUsage as DomainMemoryUsage

internal class MemoryUsageMapper {

    fun toView(domainMemoryUsageMapper: DomainMemoryUsage): ViewMemoryUsage{
        return ViewMemoryUsage(
            used = domainMemoryUsageMapper.used,
            max = domainMemoryUsageMapper.max
        )
    }
}