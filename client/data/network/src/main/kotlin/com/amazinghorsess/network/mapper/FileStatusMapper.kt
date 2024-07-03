package com.amazinghorsess.network.mapper
import com.amazinghorsess.client.repository.model.FileStatus as RepositoryFileStatus
import com.amazinghorsess.network.model.FileStatus as NetworkFileStatus

internal class FileStatusMapper {

    fun toRepository(networkFileStatus: NetworkFileStatus): RepositoryFileStatus {
        return when (networkFileStatus) {
            NetworkFileStatus.UNCHANGED -> RepositoryFileStatus.UNCHANGED
            NetworkFileStatus.NEW -> RepositoryFileStatus.NEW
            NetworkFileStatus.MODIFIED -> RepositoryFileStatus.MODIFIED
        }
    }

    fun toNetwork(repositoryFileStatus: RepositoryFileStatus): NetworkFileStatus {
        return when (repositoryFileStatus) {
            RepositoryFileStatus.UNCHANGED -> NetworkFileStatus.UNCHANGED
            RepositoryFileStatus.NEW -> NetworkFileStatus.NEW
            RepositoryFileStatus.MODIFIED -> NetworkFileStatus.MODIFIED
        }
    }
    }
