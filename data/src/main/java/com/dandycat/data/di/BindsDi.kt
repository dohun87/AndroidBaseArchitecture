package com.dandycat.data.di

import com.dandycat.data.repo.SearchRepositoryImpl
import com.dandycat.domain.repo.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsDi {

    @Singleton
    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: SearchRepositoryImpl) : SearchRepository
}