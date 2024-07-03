package com.amazinghorsess.client.config.mapper

import com.amazinghorsess.client.config.model.FileStatus as ViewFileStatus
import com.amazinghorsess.client.domain.model.FileStatus as DomainFileStatus




internal class FileStatusMapper {

    fun toView(domainFileStatus: DomainFileStatus): ViewFileStatus {
        return when (domainFileStatus) {
            DomainFileStatus.UNCHANGED -> ViewFileStatus.UNCHANGED
            DomainFileStatus.NEW -> ViewFileStatus.NEW
            DomainFileStatus.MODIFIED -> ViewFileStatus.MODIFIED
        }
    }

    fun toDomain(viewFileStatus: ViewFileStatus): DomainFileStatus {
        return when (viewFileStatus) {
            ViewFileStatus.UNCHANGED -> DomainFileStatus.UNCHANGED
            ViewFileStatus.NEW -> DomainFileStatus.NEW
            ViewFileStatus.MODIFIED -> DomainFileStatus.MODIFIED
        }
    }
}