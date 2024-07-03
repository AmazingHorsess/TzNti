package com.amazinghorsess.client.repository.mapper
import com.amazinghorsess.client.domain.model.FileNode as DomainFileNode
import com.amazinghorsess.client.repository.model.FileNode as RepositoryFileNode

internal class FileNodeMapper(
    private val fileStatusMapper: FileStatusMapper
) {

    fun toRepository(domainFileNode: DomainFileNode): RepositoryFileNode {
        return RepositoryFileNode(
            name = domainFileNode.name,
            isDirectory = domainFileNode.isDirectory,
            size = domainFileNode.size,
            status = fileStatusMapper.toRepository(domainFileNode.status),
            children = domainFileNode.children.map { toRepository(it) }
        )
    }

    fun toDomain(repositoryFileNode: RepositoryFileNode): DomainFileNode {
        return DomainFileNode(
            name = repositoryFileNode.name,
            isDirectory = repositoryFileNode.isDirectory,
            size = repositoryFileNode.size,
            status = fileStatusMapper.toDomain(repositoryFileNode.status),
            children = repositoryFileNode.children.map { toDomain(it) }
        )
    }
}