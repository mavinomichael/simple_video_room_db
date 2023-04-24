package com.mavino.videoroomdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos_table")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val url: String,
    val created_at: String,
    val watched: Int,
    val cached: Int
)
