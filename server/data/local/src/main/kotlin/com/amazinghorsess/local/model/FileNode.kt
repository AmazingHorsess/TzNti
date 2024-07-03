package com.amazinghorsess.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.amazinghorsess.local.converter.FileStatusConverter

@Entity(tableName = "file_nodes")
data class FileNode(
    @PrimaryKey(autoGenerate = true)val parentId: Long?,
    val name: String,
    val isDirectory: Boolean,
    val size: Long,
    @TypeConverters(FileStatusConverter::class) val status: FileStatus,

)
