package com.example.azimnycschools.roomdb

import androidx.room.TypeConverter
import com.example.azimnycschools.models.schools.NYCSchools
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NYCSchoolsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun nycSchoolsToString(nycSchools: NYCSchools): String = gson.toJson(nycSchools)

    @TypeConverter
    fun stringToNycSchools(data: String): NYCSchools {
        val listType = object : TypeToken<NYCSchools>() {}.type
        return gson.fromJson(data, listType)
    }
}