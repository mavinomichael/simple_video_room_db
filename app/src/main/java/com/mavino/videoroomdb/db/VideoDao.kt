package com.mavino.videoroomdb.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideoDao {

    @Insert
    suspend fun insertVideo(video: VideoEntity): Long

    @Query("SELECT * FROM videos_table")
    suspend fun getAllVideos(): List<VideoEntity>

    @Query("SELECT * FROM videos_table ORDER BY created_at ASC LIMIT (:size)")
    suspend fun getLeastRecentlyWatchedVideos(size: Int): List<VideoEntity>

    @Query("DELETE FROM videos_table WHERE id = :id")
    suspend fun deleteVideo(id: Int): Int

    @Query("""
        UPDATE videos_table
        SET 
        watched = :watched 
        WHERE id is :id""")
    suspend fun updateWatchedVideo(id: Int, watched: Int)

    @Query("""
        UPDATE videos_table
        SET 
        cached = :cached 
        WHERE id is :id""")
    suspend fun updateCachedVideo(id: Int, cached: Int): Int

}