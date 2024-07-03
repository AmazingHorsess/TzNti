package com.amazinghorsess.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "file_node_relations",
    foreignKeys = [
        ForeignKey(
            entity = FileNode::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FileNode::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("parentId"), Index("childId")]
)
data class FileNodeRelation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val parentId: Long,
    val childId: Long
)