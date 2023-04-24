package com.mavino.videoroomdb.repository

import androidx.lifecycle.LiveData
import com.mavino.videoroomdb.model.VideoEntity


interface CacheRepository {

  suspend fun updateVideo(id: Int, cached: Int): Int

  fun getVideo(id: Int): LiveData<VideoEntity>

}