package com.example.azimnycschools.repository

import com.example.azimnycschools.models.sats.Sats
import com.example.azimnycschools.models.schools.NYCSchools
import com.example.azimnycschools.roomdb.NYCSchoolsEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {
    suspend fun getSchoolsFromAPI() : Response<NYCSchools>

    suspend fun getSchoolsSATsFromAPI() : Response<Sats>

    fun getSchoolsFromDB() : Flow<NYCSchoolsEntity>

    suspend fun addSchoolsToDB(NYCSchoolsEntity: NYCSchoolsEntity)

}