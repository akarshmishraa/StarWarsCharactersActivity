package com.richmondprojects.myapplication.di

import com.example.starwarscharactersactivity.data.repository.RepositoryImpl
import com.example.starwarscharactersactivity.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(
        repositoryImpl: RepositoryImpl,
    ): Repository
}