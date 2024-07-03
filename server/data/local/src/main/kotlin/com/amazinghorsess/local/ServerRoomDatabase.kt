package com.amazinghorsess.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.amazinghorsess.local.converter.FileStatusConverter
import com.amazinghorsess.local.dao.ServerScanDao
import com.amazinghorsess.local.model.FileNode
import com.amazinghorsess.local.model.ScanResult

@Database(entities = [ScanResult::class,FileNode::class,], version = 1)
@TypeConverters(FileStatusConverter::class)
abstract class ServerRoomDatabase: RoomDatabase() {

    abstract fun serverScanDao(): ServerScanDao
}