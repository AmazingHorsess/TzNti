package com.amazinghorsess.local.di

import com.amazinghorsess.local.datasource.ServerScanLocalDataSource
import com.amazinghorsess.local.mapper.ScanResultMapper
import com.amazinghorsess.local.provider.DaoProvider
import com.amazinghorsess.local.provider.DatabaseProvider
import com.amazinghorsess.repository.datasources.ServerScanDataSource
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val localModule = module {

    singleOf(::ServerScanLocalDataSource) bind ServerScanDataSource::class

    factoryOf(::ScanResultMapper)

    singleOf(::DatabaseProvider)
    singleOf(::DaoProvider)

}