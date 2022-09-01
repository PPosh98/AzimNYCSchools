package com.example.azimnycschools.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.azimnycschools.models.schools.NYCSchools

@Entity(tableName = "nycSchools")
class NYCSchoolsEntity(val nycSchools: NYCSchools) {
    @PrimaryKey(autoGenerate = false)
    var generatedId: Int = 0
}