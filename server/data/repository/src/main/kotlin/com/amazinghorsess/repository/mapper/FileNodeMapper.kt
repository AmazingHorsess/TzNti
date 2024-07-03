package com.amazinghorsess.repository.mapper

import com.amazinghorsess.domain.model.FileStatus as DomainFileStatus
import com.amazinghorsess.repository.model.FileStatus as RepositoryFileStatus
import com.amazinghorsess.domain.model.FileNode as DomainFileNode
import com.amazinghorsess.repository.model.FileNode as RepositoryFileNode

internal class FileNodeMapper {

    fun toDomain(repositoryFileNode: RepositoryFileNode): DomainFileNode{
        return DomainFileNode(
            name = repositoryFileNode.name,
            isDirectory = repositoryFileNode.isDirectory,
            size = repositoryFileNode.size,
            status = toRepositoryStatus(repositoryFileNode.status),
            children = repositoryFileNode.children.map { toDomain(it) }
        )
    }

    fun toRepository(domainFileNode: DomainFileNode): RepositoryFileNode{
        return RepositoryFileNode(
            name = domainFileNode.name,
            isDirectory = domainFileNode.isDirectory,
            size = domainFileNode.size,
            status = toDomainStatus(domainFileNode.status),
            children = domainFileNode.children.map { toRepository(it) }
        )
    }
    private fun toRepositoryStatus(repositoryStatus: RepositoryFileStatus): DomainFileStatus {
        return when (repositoryStatus) {
            RepositoryFileStatus.NEW -> DomainFileStatus.NEW
            RepositoryFileStatus.MODIFIED -> DomainFileStatus.MODIFIED
            RepositoryFileStatus.UNCHANGED -> DomainFileStatus.UNCHANGED
        }
    }

    private fun toDomainStatus(domainStatus: DomainFileStatus): RepositoryFileStatus {
        return when (domainStatus){
            DomainFileStatus.NEW -> RepositoryFileStatus.NEW
            DomainFileStatus.MODIFIED -> RepositoryFileStatus.MODIFIED
            DomainFileStatus.UNCHANGED -> RepositoryFileStatus.UNCHANGED
        }

    }

}