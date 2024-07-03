package com.amazinghorsess.scanlist.di

import com.amazinghorsess.scanlist.mapper.ScanResultMapper
import com.amazinghorsess.scanlist.presentation.ScanListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val scanListModule = module {
    viewModelOf(::ScanListViewModel)
    factoryOf(::ScanResultMapper)
}