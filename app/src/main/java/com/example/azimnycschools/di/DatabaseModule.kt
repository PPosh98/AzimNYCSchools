package com.example.azimnycschools.di

import android.content.Context
import androidx.room.Room
import com.example.azimnycschools.roomdb.NYCSchoolsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NYCSchoolsDatabase::class.java,
        "NYCSchoolsDatabase"
    ).build()

    @Provides
    fun provideNycSchoolsDao(database: NYCSchoolsDatabase) = database.nycSchoolsDAO()
}