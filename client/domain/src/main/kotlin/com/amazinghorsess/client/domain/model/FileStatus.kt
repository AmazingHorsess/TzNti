package com.amazinghorsess.client.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class FileStatus {

    UNCHANGED,

    NEW,

    MODIFIED,

}