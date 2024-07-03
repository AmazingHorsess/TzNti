package com.amazinghorsess.client.repository.model

import kotlinx.serialization.Serializable

@Serializable
enum class FileStatus {

    UNCHANGED,

    NEW,

    MODIFIED,

}