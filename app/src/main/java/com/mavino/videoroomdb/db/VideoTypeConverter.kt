package com.mavino.videoroomdb.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mavino.videoroomdb.model.VideoEntity

class VideoTypeConverter {

    @TypeConverter
    fun fromVideoObject(value: List<VideoEntity>): String{
        val gson = Gson()
        val type = object : TypeToken<List<VideoEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toVideoObject(value: String): List<VideoEntity>{
        val gson = Gson()
        val type = object : TypeToken<List<VideoEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}