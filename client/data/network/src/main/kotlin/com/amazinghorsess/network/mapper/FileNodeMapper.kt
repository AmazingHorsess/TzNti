package com.amazinghorsess.network.mapper
import com.amazinghorsess.client.repository.model.FileStatus
import com.amazinghorsess.client.repository.model.FileNode as RepositoryFileNode
import com.amazinghorsess.network.model.FileNode as NetworkFileNode

internal class FileNodeMapper(

) {

    fun toRepository(networkFileNode: NetworkFileNode): RepositoryFileNode {
        return RepositoryFileNode(
            name = networkFileNode.name,
            isDirectory = networkFileNode.isDirectory,
            size = networkFileNode.size,
            status = networkFileNode.status.toFileStatus(),
            children = networkFileNode.children?.map { toRepository(it) } ?: emptyList()
        )
    }

    fun toNetwork(repositoryFileNode: RepositoryFileNode): NetworkFileNode {
        return NetworkFileNode(
            name = repositoryFileNode.name,
            isDirectory = repositoryFileNode.isDirectory,
            size = repositoryFileNode.size,
            status = repositoryFileNode.status.toString(),
            children = repositoryFileNode.children.map { toNetwork(it) }
        )
    }
    private fun String.toFileStatus(): FileStatus {
        return when (this.toUpperCase()) {
            "UNCHANGED" -> FileStatus.UNCHANGED
            "NEW" -> FileStatus.NEW
            "MODIFIED" -> FileStatus.MODIFIED
            else -> throw IllegalArgumentException("Unknown file status: $this")
        }
    }
}