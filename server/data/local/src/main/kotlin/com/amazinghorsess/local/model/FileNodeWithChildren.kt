package com.amazinghorsess.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class FileNodeWithChildren(
    @Embedded val fileNode: FileNode,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    )
    val children: List<FileNodeWithChildren>
)