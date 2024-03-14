package com.example.elalmashop.data.db.local


import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun fromListToJson(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromJsonToList(json: String): List<String>{
        val gson = Gson()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }

}