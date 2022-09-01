package com.example.azimnycschools.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.azimnycschools.roomdb.NYCSchoolsEntity
import com.example.azimnycschools.roomdb.NYCSchoolsTypeConverter
import com.example.azimnycschools.roomdb.NYCSchoolsDAO

@Database(
    entities = [NYCSchoolsEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(NYCSchoolsTypeConverter::class)
abstract class NYCSchoolsDatabase : RoomDatabase() {
    abstract fun nycSchoolsDAO() : NYCSchoolsDAO
}