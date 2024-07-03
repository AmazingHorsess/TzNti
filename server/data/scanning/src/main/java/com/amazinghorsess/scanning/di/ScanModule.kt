package com.amazinghorsess.scanning.di

import com.amazinghorsess.domain.manager.ScanManager
import com.amazinghorsess.scanning.ScanManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scanModule = module {
    single { ScanManagerImpl( ) } bind ScanManager::class
}