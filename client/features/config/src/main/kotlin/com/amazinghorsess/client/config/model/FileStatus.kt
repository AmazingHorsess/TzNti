package com.amazinghorsess.client.config.model

import kotlinx.serialization.Serializable

@Serializable
enum class FileStatus {

    UNCHANGED,

    NEW,

    MODIFIED,

}