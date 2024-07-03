package com.amazinghorsess.local.converter

import androidx.room.TypeConverter
import com.amazinghorsess.local.model.FileStatus

internal class FileStatusConverter {

  @TypeConverter
  fun fromFileStatus(status: FileStatus): String = status.name

    @TypeConverter
    fun toFileStatus(fileStatus: String): FileStatus = FileStatus.valueOf(fileStatus)

}