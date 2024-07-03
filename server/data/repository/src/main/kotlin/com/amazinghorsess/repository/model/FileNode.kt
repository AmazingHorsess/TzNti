package com.amazinghorsess.repository.model


data class FileNode(
    val name: String,
    val isDirectory: Boolean,
    val size: Long,
    val status: FileStatus,
    val children: List<FileNode> = emptyList()
)
