package com.amazinghorsess.client.config.mapper

import com.amazinghorsess.client.config.model.FileNode as ViewFileNode
import com.amazinghorsess.client.domain.model.FileNode as DomainFileNode

internal class FileNodeMapper(
    private val statusMapper: FileStatusMapper
) {

    fun toView(domainFileNode: DomainFileNode): ViewFileNode {
        return ViewFileNode(
            name = domainFileNode.name,
            isDirectory = domainFileNode.isDirectory,
            size = domainFileNode.size,
            status = statusMapper.toView(domainFileNode.status),
            children = domainFileNode.children.map { toView(it) }
        )
    }
    fun toDomain(viewFileNode: ViewFileNode): DomainFileNode {
        return DomainFileNode(
            name = viewFileNode.name,
            isDirectory = viewFileNode.isDirectory,
            size = viewFileNode.size,
            status = statusMapper.toDomain(viewFileNode.status),
            children = viewFileNode.children.map { toDomain(it) }
        )
    }

}