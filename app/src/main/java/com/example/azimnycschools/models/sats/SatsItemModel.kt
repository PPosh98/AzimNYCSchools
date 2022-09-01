package com.example.azimnycschools.models.sats


import com.google.gson.annotations.SerializedName

data class SatsItemModel(
    @SerializedName("dbn")
    val dbn: String = "N/A",
    @SerializedName("num_of_sat_test_takers")
    val numOfSatTestTakers: String = "N/A",
    @SerializedName("sat_critical_reading_avg_score")
    val satCriticalReadingAvgScore: String = "N/A",
    @SerializedName("sat_math_avg_score")
    val satMathAvgScore: String = "N/A",
    @SerializedName("sat_writing_avg_score")
    val satWritingAvgScore: String = "N/A",
    @SerializedName("school_name")
    val schoolName: String = "N/A"
)