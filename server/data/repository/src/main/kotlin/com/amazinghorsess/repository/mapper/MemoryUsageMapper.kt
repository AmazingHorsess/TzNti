package com.amazinghorsess.repository.mapper

import com.amazinghorsess.repository.model.MemoryUsage as RepositoryMemoryUsage
import com.amazinghorsess.domain.model.MemoryUsage as DomainMemoryUsage

internal class MemoryUsageMapper {

    fun toRepository(domainMemoryUsage: DomainMemoryUsage): RepositoryMemoryUsage{
        return RepositoryMemoryUsage(
            used = domainMemoryUsage.used,
            max = domainMemoryUsage.max
        )

    }

    fun toDomain(repositoryMemoryUsage: RepositoryMemoryUsage): DomainMemoryUsage{
        return DomainMemoryUsage(
            used = repositoryMemoryUsage.used,
            max = repositoryMemoryUsage.max
        )
    }

}