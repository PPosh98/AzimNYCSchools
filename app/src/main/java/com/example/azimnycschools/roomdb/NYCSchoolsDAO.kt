package com.example.azimnycschools.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NYCSchoolsDAO {

    @Query("SELECT * FROM nycSchools")
    fun readSchoolsFromDb() : Flow<NYCSchoolsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchoolsToDb(NYCSchoolsEntity: NYCSchoolsEntity)
}