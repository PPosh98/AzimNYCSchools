package com.example.azimnycschools.repository

import android.util.Log
import com.example.azimnycschools.api.FetchAPI
import com.example.azimnycschools.models.sats.Sats
import com.example.azimnycschools.models.schools.NYCSchools
import com.example.azimnycschools.repository.Repository
import com.example.azimnycschools.roomdb.NYCSchoolsDAO
import com.example.azimnycschools.roomdb.NYCSchoolsEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val fetchAPI: FetchAPI, private val NYCSchoolsDAO: NYCSchoolsDAO) :
    Repository {
    override suspend fun getSchoolsFromAPI(): Response<NYCSchools> =
        fetchAPI.getSchools()

    override suspend fun getSchoolsSATsFromAPI(): Response<Sats> =
        fetchAPI.getSATs()

    override fun getSchoolsFromDB(): Flow<NYCSchoolsEntity> =
        NYCSchoolsDAO.readSchoolsFromDb()


    override suspend fun addSchoolsToDB(nycSchoolsEntity: NYCSchoolsEntity) =
        NYCSchoolsDAO.insertSchoolsToDb(nycSchoolsEntity)
}