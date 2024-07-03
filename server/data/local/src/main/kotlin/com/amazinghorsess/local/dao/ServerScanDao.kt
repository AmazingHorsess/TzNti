package com.amazinghorsess.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amazinghorsess.local.model.ScanResult

@Dao
interface ServerScanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scan: ScanResult)

    @Query("SELECT * FROM scan_results")
    suspend fun getAllScans(): List<ScanResult>

    @Query("SELECT * FROM scan_results WHERE id = :id")
    suspend fun getScanById(id: Int): ScanResult?

    @Query("SELECT MAX(id) FROM scan_results")
    suspend fun getMaxId(): Int?
}