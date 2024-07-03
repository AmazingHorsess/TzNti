package com.amazinghorsess.local.model

import androidx.room.Entity


@Entity
enum class ServerStatus(val id: Int){

    STOPPED(0),

    RUNNING(1),

}


