package com.amazinghorsess.main.di

import com.amazinghorsess.main.mapper.MemoryUsageMapper
import com.amazinghorsess.main.mapper.ServerStatusMapper
import com.amazinghorsess.main.presentation.FileViewModel
import com.amazinghorsess.main.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mainModule = module {

    viewModelOf(::MainViewModel)
    viewModelOf(::FileViewModel)


    factoryOf(::ServerStatusMapper)
    factoryOf(::MemoryUsageMapper)
}