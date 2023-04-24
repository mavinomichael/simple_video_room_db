package com.mavino.videoroomdb.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mavino.videoroomdb.model.CacheObserver

@Dao
interface ObserverDao {

    @Insert
    fun insertObserver(observer: CacheObserver)
}