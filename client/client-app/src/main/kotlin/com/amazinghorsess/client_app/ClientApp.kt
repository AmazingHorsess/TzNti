package com.amazinghorsess.client_app

import android.app.Application
import com.amazinghorsess.client.config.di.configModule
import com.amazinghorsess.client.repository.di.repositoryModule
import com.amazinghorsess.network.di.networkModule
import com.amazinghorsess.scanlist.di.scanListModule
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
class ClientApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            logger(AndroidLogger(Level.DEBUG))

            modules(getAllModules())
        }

    }
    private fun getAllModules(): List<Module> = listOf(
        networkModule,
        configModule,
        repositoryModule,
        scanListModule,

    )
}
