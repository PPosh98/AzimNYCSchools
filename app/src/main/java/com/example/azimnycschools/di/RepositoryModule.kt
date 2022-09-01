package com.example.azimnycschools.di

import com.example.azimnycschools.repository.Repository
import com.example.azimnycschools.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun getRepository(repository: RepositoryImpl): Repository
}