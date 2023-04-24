package com.mavino.videoroomdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "observer")
data class CacheObserver(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String,
    val cached: Int
)
