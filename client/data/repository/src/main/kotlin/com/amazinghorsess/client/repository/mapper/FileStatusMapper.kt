package com.amazinghorsess.client.repository.mapper
import com.amazinghorsess.client.domain.model.FileStatus as DomainFileStatus
import com.amazinghorsess.client.repository.model.FileStatus as RepositoryFileStatus

internal class FileStatusMapper {

    fun toRepository(domainFileStatus: DomainFileStatus): RepositoryFileStatus {
        return when (domainFileStatus) {
            DomainFileStatus.UNCHANGED -> RepositoryFileStatus.UNCHANGED
            DomainFileStatus.NEW -> RepositoryFileStatus.NEW
            DomainFileStatus.MODIFIED -> RepositoryFileStatus.MODIFIED
        }
    }

    fun toDomain(repositoryFileStatus: RepositoryFileStatus): DomainFileStatus {
        return when (repositoryFileStatus) {
            RepositoryFileStatus.UNCHANGED -> DomainFileStatus.UNCHANGED
            RepositoryFileStatus.NEW -> DomainFileStatus.NEW
            RepositoryFileStatus.MODIFIED -> DomainFileStatus.MODIFIED
        }
    }
}