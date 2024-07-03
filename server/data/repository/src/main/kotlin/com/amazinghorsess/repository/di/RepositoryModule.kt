package com.amazinghorsess.repository.di

import com.amazinghorsess.domain.repository.ServerScanRepository
import com.amazinghorsess.repository.ServerScanRepositoryImpl
import com.amazinghorsess.repository.mapper.FileNodeMapper
import com.amazinghorsess.repository.mapper.MemoryUsageMapper
import com.amazinghorsess.repository.mapper.ScanResultMapper
import com.amazinghorsess.repository.mapper.ServerConfigMapper
import com.amazinghorsess.repository.mapper.ServerStatusMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::ServerScanRepositoryImpl) bind ServerScanRepository::class

    factoryOf(::ScanResultMapper)
    factoryOf(::ServerConfigMapper)
    factoryOf(::ServerStatusMapper)
    factoryOf(::MemoryUsageMapper)
    factoryOf(::FileNodeMapper)


}