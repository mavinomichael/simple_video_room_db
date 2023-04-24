package com.mavino.videoroomdb.db

import androidx.room.*
import com.mavino.videoroomdb.model.CacheObserver
import com.mavino.videoroomdb.model.VideoEntity

@TypeConverters(VideoTypeConverter::class)
@Database(entities = [VideoEntity::class, CacheObserver::class], version = 1, exportSchema = true)
abstract class VideoDatabase: RoomDatabase() {
    abstract fun videoDao(): VideoDao
    abstract fun ObserverDao(): ObserverDao

    companion object{
        const val DATABASE_NAME = "video"
    }
}