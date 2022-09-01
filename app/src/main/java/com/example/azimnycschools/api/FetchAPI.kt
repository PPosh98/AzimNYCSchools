package com.example.azimnycschools.api

import com.example.azimnycschools.models.sats.Sats
import com.example.azimnycschools.models.schools.NYCSchools
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchAPI {
    @GET("s3k6-pzi2.json")
    suspend fun getSchools() : Response<NYCSchools>

    @GET("f9bf-2cp4.json")
    suspend fun getSATs() : Response<Sats>
}