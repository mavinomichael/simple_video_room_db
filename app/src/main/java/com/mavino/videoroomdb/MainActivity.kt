package com.mavino.videoroomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.room.Room
import com.mavino.videoroomdb.db.VideoDao
import com.mavino.videoroomdb.db.VideoDatabase
import com.mavino.videoroomdb.model.VideoEntity
import com.mavino.videoroomdb.viewmodel.MainViewModel
import com.mavino.videoroomdb.viewmodel.MainViewModel.Companion.TAG
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var dao: VideoDao

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val db = Room.databaseBuilder(
            this,
            VideoDatabase::class.java,
            VideoDatabase.DATABASE_NAME
        ).build()

        dao = db.videoDao()

        //viewModel.insertVideos(dao, getVideoData())
        viewModel.executeWorker()

        //viewModel.getAllVideos(dao)

        //viewModel.updateVideoAsCached(dao, 1)
        subscribeObservers()
    }


    private fun getVideoData(): List<VideoEntity> {

        return listOf(
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/maye_tobs_the_kimanuel_set_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/zizi_cardow_1_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/dyre_design_damini_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/dose_of_kasa_carter_jacket_nemi_2_piece_set_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/soluch_by_soso_miaco_3_piece_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/cuz_set_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/krenitive_peony_dress_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/kkclothing_big_hunnie_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/soluch_by_soso_tresh_set_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/copy_of_beunique_1_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/luxury_zoma_dress_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/krentive_style_wavy_lounge_set_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/lily_midi_dress_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/krentive_style_krentive_style_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/417a9486-03d3-4eb3-9214-536768e2f234_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/fc522831-d106-4a84-8725-9b87ff46776a_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/05527c55-1764-430f-a253-be4ad2213347_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://edekee-m3u8.s3.us-east-2.amazonaws.com/ba9439bf-2220-4d82-aed3-9dcf3cec9742_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
            VideoEntity(
                url = "https://d3usnkytdvge4t.cloudfront.net/ca8653f1-3ed3-4dce-b13d-d9093a1f0c53_1.m3u8",
                created_at = getCurrentTimestamp(),
                watched = 0,
                cached = 0
            ),
        )
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

    private fun subscribeObservers(){
        viewModel.purgeResponse.observe(this){
            Log.d(TAG, "subscribeObservers: purge response $it")
        }

        viewModel.video.observe(this){
            Log.d(TAG, "subscribeObservers: a new video was cached 1")
        }
    }

}