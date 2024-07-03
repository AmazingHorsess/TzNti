package com.amazinghorsess.memory.di

import com.amazinghorsess.domain.manager.MemoryManager
import com.amazinghorsess.memory.MemoryManagerImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val memoryModule = module {
    singleOf(::MemoryManagerImpl) bind MemoryManager::class
}