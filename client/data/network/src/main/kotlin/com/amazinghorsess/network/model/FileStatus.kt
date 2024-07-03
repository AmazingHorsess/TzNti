package com.amazinghorsess.network.model

import kotlinx.serialization.Serializable

@Serializable
enum class FileStatus {

    UNCHANGED,

    NEW,

    MODIFIED,

}