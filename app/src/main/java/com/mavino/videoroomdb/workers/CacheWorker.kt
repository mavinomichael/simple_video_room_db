package com.mavino.videoroomdb.workers

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mavino.videoroomdb.db.VideoDatabase
import com.mavino.videoroomdb.repository.CacheRepositoryImpl
import com.mavino.videoroomdb.viewmodel.MainViewModel.Companion.TAG

class CacheWorker(
    context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {

    val repo = CacheRepositoryImpl(applicationContext)

    val db = Room.databaseBuilder(
        applicationContext,
        VideoDatabase::class.java,
        VideoDatabase.DATABASE_NAME
    ).build()

    val dao = db.videoDao()

    override suspend fun doWork(): Result {

        val video = repo.getVideo(1)
        Log.d(TAG, "doWork: worker is running")
        repo.updateVideo(1, 0)
        //call associate notify fun here
        dao.getAllVideos()

        val id = video.value?.id
        Log.d(TAG, "doWork: $id")

        return Result.success()
    }
}