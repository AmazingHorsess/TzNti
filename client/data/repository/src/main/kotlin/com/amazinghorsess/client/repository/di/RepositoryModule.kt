package com.amazinghorsess.client.repository.di

import com.amazinghorsess.client.domain.repository.ServerRepository
import com.amazinghorsess.client.repository.ServerRepositoryImpl
import com.amazinghorsess.client.repository.mapper.FileNodeMapper
import com.amazinghorsess.client.repository.mapper.FileStatusMapper
import com.amazinghorsess.client.repository.mapper.MemoryUsageMapper
import com.amazinghorsess.client.repository.mapper.ScanResultMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ServerRepositoryImpl) bind ServerRepository::class

    factoryOf(::MemoryUsageMapper)
    factoryOf(::FileNodeMapper)
    factoryOf(::FileStatusMapper)
    factoryOf(::ScanResultMapper)

}