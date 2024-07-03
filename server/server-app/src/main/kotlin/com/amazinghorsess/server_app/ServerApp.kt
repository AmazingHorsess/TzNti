package com.amazinghorsess.server_app

import android.app.Application
import com.amazinghorsess.archiving.di.archiveModule
import com.amazinghorsess.domain.di.domainModule
import com.amazinghorsess.local.di.localModule
import com.amazinghorsess.main.di.mainModule
import com.amazinghorsess.memory.di.memoryModule
import com.amazinghorsess.network.di.networkModule
import com.amazinghorsess.repository.di.repositoryModule
import com.amazinghorsess.scanning.di.scanModule
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.android.ext.koin.androidContext


class ServerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@ServerApp)
            logger(AndroidLogger(Level.DEBUG))

            modules(getAllModules())
        }

    }
}
private fun getAllModules(): List<Module> = listOf(
    repositoryModule,
    domainModule,
    networkModule,
    mainModule,
    memoryModule,
    scanModule,
    localModule,
    archiveModule,

)