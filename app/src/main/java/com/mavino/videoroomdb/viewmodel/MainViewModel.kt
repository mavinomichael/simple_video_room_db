package com.mavino.videoroomdb.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.mavino.videoroomdb.db.VideoDao
import com.mavino.videoroomdb.db.VideoDatabase
import com.mavino.videoroomdb.model.VideoEntity
import com.mavino.videoroomdb.repository.CacheRepository
import com.mavino.videoroomdb.repository.CacheRepositoryImpl
import com.mavino.videoroomdb.workers.CacheWorker
import kotlinx.coroutines.launch

class MainViewModel(application: Application): ViewModel() {

    private var _alert: MutableLiveData<Int> = MutableLiveData()
    val alert: LiveData<Int> = _alert

    private var _videos: MutableLiveData<List<VideoEntity>> = MutableLiveData()
    val videos: LiveData<List<VideoEntity>> = _videos

    private var _leastWachedVideos: MutableLiveData<List<VideoEntity>> = MutableLiveData()
    val leastWachedVideos: LiveData<List<VideoEntity>> = _leastWachedVideos

    private var _purgeResponse: MutableLiveData<Int> = MutableLiveData()
    val purgeResponse: LiveData<Int> = _purgeResponse

    lateinit var workManager: WorkManager

    lateinit var repository: CacheRepository

    val db = Room.databaseBuilder(
        application,
        VideoDatabase::class.java,
        VideoDatabase.DATABASE_NAME
    ).build()

    var dao = db.videoDao()

    private var _video: MutableLiveData<VideoEntity> = MutableLiveData()
    val video: LiveData<List<VideoEntity>> = dao.getAllVideos()

    init {
        workManager = WorkManager.getInstance(application)
        dao = db.videoDao()
        repository = CacheRepositoryImpl(application)
    }

    fun insertVideos(dao: VideoDao, videoData: List<VideoEntity>){
        viewModelScope.launch {
            videoData.onEach {
                dao.insertVideo(it)
                Log.d(TAG, "insertVideos: $it")
            }
        }
    }

    fun getAllVideos(dao: VideoDao){
        viewModelScope.launch {
            val videos = dao.getAllVideos()
            Log.d(TAG, "getAllVideos: ${videos.value?.size}")
            videos.value?.onEach {
                Log.d(TAG, "getAllVideos: $it")
            }
        }
    }

    fun getLeastWatched(dao: VideoDao, size: Int): List<VideoEntity>?{
        var videos : List<VideoEntity>? = null
        viewModelScope.launch {
            videos = dao.getLeastRecentlyWatchedVideos(size)
            Log.d(TAG, "getLeastWatchedVideos: ${videos!!.size}")
            Log.d(TAG, "getLeastWatchedVideos: $videos")
            deleteVideos(videos!!, dao)
        }
        return videos
    }

    fun deleteVideos(videos: List<VideoEntity>, dao: VideoDao){
        viewModelScope.launch {
            videos.onEach {
                dao.deleteVideo(it.id!!)
                Log.d(TAG, "deleteVideo: $it")
            }
        }
    }

    fun updateVideoAsCached(dao: VideoDao, id: Int){
        viewModelScope.launch {
            val response = dao.updateCachedVideo(id, CACHED)

            _purgeResponse.value = response
        }
    }

    fun updatedVideoAsWatched(dao: VideoDao, id: Int){
        viewModelScope.launch {
            dao.updateWatchedVideo(id, WATCHED)
        }
    }

    fun purgeWatchedVideos(dao: VideoDao, purgeSize: Int){
        viewModelScope.launch {
            val videos = dao.getLeastRecentlyWatchedVideos(5)
            videos.onEach {
                if (it.watched == WATCHED){
                    dao.deleteVideo(it.id!!)
                    //remove from cache
                }
            }
        }
    }

    fun getVideo(id: Int){
        viewModelScope.launch {
            val video = repository.getVideo(id)
            _video.value = video.value
        }
    }

    fun executeWorker(){
        workManager.enqueue(OneTimeWorkRequest.from(CacheWorker::class.java))
    }

    class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    companion object{
        const val TAG = "AppDebug"
        const val WATCHED = 1
        const val CACHED = 1
    }
}