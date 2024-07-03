package com.amazinghorsess.domain.interactor

import com.amazinghorsess.domain.model.MemoryUsage
import com.amazinghorsess.domain.model.ScanResult
import com.amazinghorsess.domain.model.ServerConfig
import com.amazinghorsess.domain.model.ServerStatus
import kotlinx.coroutines.flow.Flow

interface ServerInteractor {

    suspend fun start(port: Int)
    suspend fun stop()

}