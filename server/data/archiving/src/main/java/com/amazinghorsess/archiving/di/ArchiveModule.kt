package com.amazinghorsess.archiving.di

import com.amazinghorsess.archiving.ArchiveManagerImpl
import com.amazinghorsess.domain.manager.ArchiveManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val archiveModule = module {

    single { ArchiveManagerImpl(androidContext(), get()) } bind ArchiveManager::class
}

