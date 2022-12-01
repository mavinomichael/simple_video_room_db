package com.mavino.videoroomdb.db

import androidx.room.*

@TypeConverters(VideoTypeConverter::class)
@Database(entities = [VideoEntity::class], version = 1, exportSchema = true)
abstract class VideoDatabase: RoomDatabase() {
    abstract fun videoDao(): VideoDao

    companion object{
        const val DATABASE_NAME = "video"
    }
}