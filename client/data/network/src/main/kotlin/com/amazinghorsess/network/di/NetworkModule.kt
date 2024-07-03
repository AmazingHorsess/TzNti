package com.amazinghorsess.network.di


import com.amazinghorsess.client.repository.datasources.ServerDataSource
import com.amazinghorsess.network.datasource.ServerRepositoryNetworkDataSource
import com.amazinghorsess.network.ktor.websockets.ApiService
import com.amazinghorsess.network.ktor.websockets.ApiServiceImpl
import com.amazinghorsess.network.ktor.websockets.WebSocketClient
import com.amazinghorsess.network.mapper.FileNodeMapper
import com.amazinghorsess.network.mapper.FileStatusMapper
import com.amazinghorsess.network.mapper.MemoryUsageMapper
import com.amazinghorsess.network.mapper.ScanResultMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::WebSocketClient)
    singleOf(::ServerRepositoryNetworkDataSource) bind ServerDataSource::class


    singleOf(::ApiServiceImpl) bind ApiService::class


    factoryOf(::FileStatusMapper)
    factoryOf(::FileNodeMapper)
    factoryOf(::MemoryUsageMapper)
    factoryOf(::ScanResultMapper)


}