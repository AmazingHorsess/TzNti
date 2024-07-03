package com.amazinghorsess.main.mapper

import com.amazinghorsess.main.model.MemoryUsage as ViewMemoryUsage
import com.amazinghorsess.domain.model.MemoryUsage as DomainMemoryUsage

internal class MemoryUsageMapper{

    fun toView(domainMemoryUsage: DomainMemoryUsage): ViewMemoryUsage{
        return ViewMemoryUsage(
            used = domainMemoryUsage.used,
            max = domainMemoryUsage.max
        )
    }
}