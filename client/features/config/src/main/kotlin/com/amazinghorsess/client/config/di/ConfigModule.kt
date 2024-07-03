package com.amazinghorsess.client.config.di

import com.amazinghorsess.client.config.ConfigViewModel
import com.amazinghorsess.client.config.mapper.FileNodeMapper
import com.amazinghorsess.client.config.mapper.FileStatusMapper
import com.amazinghorsess.client.config.mapper.MemoryUsageMapper
import com.amazinghorsess.client.config.mapper.ScanResultMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val configModule = module {
    viewModelOf(::ConfigViewModel)

    factoryOf(::MemoryUsageMapper)

    factoryOf(::FileStatusMapper)

    factoryOf(::FileNodeMapper)
    factoryOf(::ScanResultMapper)
}