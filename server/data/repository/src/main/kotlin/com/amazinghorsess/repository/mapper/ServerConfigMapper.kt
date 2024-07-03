package com.amazinghorsess.repository.mapper

import com.amazinghorsess.domain.model.ServerConfig as DomainServerConfig
import com.amazinghorsess.repository.model.ServerConfig as RepositoryServerConfig

internal class ServerConfigMapper {

    fun toDomain(repositoryServerConfig: RepositoryServerConfig): DomainServerConfig{
        return DomainServerConfig(
            port = repositoryServerConfig.port,
        )
    }

    fun toRepository(domainServerConfig: DomainServerConfig): RepositoryServerConfig{
        return RepositoryServerConfig(
            port = domainServerConfig.port,
        )
    }
}