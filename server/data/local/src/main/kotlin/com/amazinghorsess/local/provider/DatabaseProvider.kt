package com.amazinghorsess.local.provider

import android.content.Context
import androidx.room.Room
import com.amazinghorsess.local.ServerRoomDatabase

class DatabaseProvider(
    private val context: Context,
) {
    private var database: ServerRoomDatabase? = null

    fun getInstance(): ServerRoomDatabase =
        database ?: synchronized(this) {
            database ?: buildDatabase().also { database = it }
        }

    private fun buildDatabase(): ServerRoomDatabase =
        Room.databaseBuilder(context, ServerRoomDatabase::class.java, "server-db")
            .build()

}