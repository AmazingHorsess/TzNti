package com.amazinghorsess.client.config.model

import kotlinx.serialization.Serializable

@Serializable
data class FileNode(
    val name: String,
    val isDirectory: Boolean,
    val size: Long,
    var status: FileStatus,
    val children: List<FileNode> = emptyList()
)
