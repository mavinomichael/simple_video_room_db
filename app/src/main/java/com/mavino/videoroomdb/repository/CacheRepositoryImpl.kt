package com.mavino.videoroomdb.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.mavino.videoroomdb.db.VideoDatabase
import com.mavino.videoroomdb.model.VideoEntity

class CacheRepositoryImpl(context: Context): CacheRepository {
    private val db = Room.databaseBuilder(
        context,
        VideoDatabase::class.java,
        VideoDatabase.DATABASE_NAME
    ).build()

    private val dao = db.videoDao()

    override suspend fun updateVideo(id: Int, cached: Int): Int {
        return dao.updateCachedVideo(id, cached)
    }

    override fun getVideo(id: Int): LiveData<VideoEntity> {
        return dao.getVideo(id)
    }
}