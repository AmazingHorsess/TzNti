package com.amazinghorsess.client.repository.mapper
import com.amazinghorsess.client.domain.model.MemoryUsage as DomainMemoryUsage
import com.amazinghorsess.client.repository.model.MemoryUsage as RepositoryMemoryUsage

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