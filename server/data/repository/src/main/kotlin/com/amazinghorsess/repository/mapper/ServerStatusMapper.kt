package com.amazinghorsess.repository.mapper

import com.amazinghorsess.domain.model.ServerStatus as DomainServerStatus
import com.amazinghorsess.repository.model.ServerStatus as RepositoryServerStatus

internal class ServerStatusMapper {

    fun toDomain(repositoryServerStatus: RepositoryServerStatus): DomainServerStatus =
        when(repositoryServerStatus){
            RepositoryServerStatus.RUNNING -> DomainServerStatus.RUNNING
            RepositoryServerStatus.STOPPED -> DomainServerStatus.STOPPED
        }

    fun toReposiotry(domainServerStatus: DomainServerStatus): RepositoryServerStatus =
        when(domainServerStatus){
            DomainServerStatus.RUNNING -> RepositoryServerStatus.RUNNING
            DomainServerStatus.STOPPED -> RepositoryServerStatus.STOPPED
        }



}