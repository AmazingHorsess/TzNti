package com.amazinghorsess.network.model

import kotlinx.serialization.Serializable


@Serializable
data class FileNode(
    val name: String,
    val isDirectory: Boolean,
    val size: Long,
    val status: String,
    val children: List<FileNode>? = null
)