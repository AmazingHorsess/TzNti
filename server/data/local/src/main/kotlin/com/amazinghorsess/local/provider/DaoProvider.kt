package com.amazinghorsess.local.provider

import com.amazinghorsess.local.dao.ServerScanDao

class DaoProvider(private val database: DatabaseProvider){
    fun getServerScanDao(): ServerScanDao =
        database.getInstance().serverScanDao()
}
