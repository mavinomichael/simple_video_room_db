package com.mavino.videoroomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.mavino.videoroomdb.db.VideoDao
import com.mavino.videoroomdb.db.VideoDatabase
import com.mavino.videoroomdb.db.VideoEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            this,
            VideoDatabase::class.java,
            VideoDatabase.DATABASE_NAME
        ).build()


        val dao = db.videoDao()

        //insert videos
        //insertVideos(dao)

        //getAllVideos(dao)

        //getLeastWatched(dao)

        //update as cached
        //updateVideoAsCached(dao)

        //update as watched
        //updatedVideoAsWatched(dao)

        //purge
        //purgeWatchedVideos(dao, 5)

    }


    private suspend fun getVideoData(): List<VideoEntity>{
        val urls = ArrayList<VideoEntity>()
        val list = listOf(
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/maye_tobs_the_kimanuel_set_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/zizi_cardow_1_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/dyre_design_damini_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/dose_of_kasa_carter_jacket_nemi_2_piece_set_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/soluch_by_soso_miaco_3_piece_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/cuz_set_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/krenitive_peony_dress_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/kkclothing_big_hunnie_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/soluch_by_soso_tresh_set_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/copy_of_beunique_1_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/luxury_zoma_dress_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/krentive_style_wavy_lounge_set_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/lily_midi_dress_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/krentive_style_krentive_style_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/417a9486-03d3-4eb3-9214-536768e2f234_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/fc522831-d106-4a84-8725-9b87ff46776a_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/05527c55-1764-430f-a253-be4ad2213347_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://edekee-m3u8.s3.us-east-2.amazonaws.com/ba9439bf-2220-4d82-aed3-9dcf3cec9742_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
            VideoEntity(url = "https://d3usnkytdvge4t.cloudfront.net/ca8653f1-3ed3-4dce-b13d-d9093a1f0c53_1.m3u8", created_at = getCurrentTimestamp(), watched = 0, cached = 0),
        )
        list.onEach {
        //delay(3000)
            urls.add(VideoEntity(url = it.url, created_at = getCurrentTimestamp(), watched = 0, cached = 0))
            Log.d(TAG, "getVideoData: created $it")
        }
        return urls
    }

    // dates format looks like this: "2019-07-23 HH:mm:ss"
    private fun getCurrentTimestamp(): String {
        return getDateFormat().format(Date())
    }

    private fun getDateFormat(): SimpleDateFormat{
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getDefault()
        return sdf
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun insertVideos(dao: VideoDao){
        GlobalScope.launch {
            getVideoData().onEach {
                dao.insertVideo(it)
                Log.d(TAG, "insertVideos: $it")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getAllVideos(dao: VideoDao){
        GlobalScope.launch {
            val videos = dao.getAllVideos()
            Log.d(TAG, "getAllVideos: ${videos.size}")
            videos.onEach {
                Log.d(TAG, "getAllVideos: $it")
            }


        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getLeastWatched(dao: VideoDao, size: Int): List<VideoEntity>?{
        var videos : List<VideoEntity>? = null
        GlobalScope.launch {
            videos = dao.getLeastRecentlyWatchedVideos(size)
            Log.d(TAG, "getLeastWatchedVideos: ${videos!!.size}")
            Log.d(TAG, "getLeastWatchedVideos: $videos")
            deleteVideos(videos!!, dao)
        }
        return videos
    }
    
    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteVideos(videos: List<VideoEntity>, dao: VideoDao){
        GlobalScope.launch {
            videos.onEach {
                dao.deleteVideo(it.id!!)
                Log.d(TAG, "deleteVideo: $it")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateVideoAsCached(dao: VideoDao){
        GlobalScope.launch {
            for (i in 1..10){
                dao.updateCachedVideo(i, 1)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updatedVideoAsWatched(dao: VideoDao){
        GlobalScope.launch {
            for (i in 1..10){
                dao.updateWatchedVideo(i, 1)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun purgeWatchedVideos(dao: VideoDao, purgeSize: Int){
        GlobalScope.launch {
            val videos = dao.getLeastRecentlyWatchedVideos(5)
            videos.onEach {
                if (it.watched == 1){
                    dao.deleteVideo(it.id!!)
                    //remove from cache
                }
            }
        }
    }

    companion object{
        const val TAG = "AppDebug"
    }
}