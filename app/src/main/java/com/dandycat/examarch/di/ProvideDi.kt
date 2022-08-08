package com.dandycat.examarch.di

import android.content.Context
import com.dandycat.domain.repo.SearchRepository
import com.dandycat.domain.usecase.SearchUseCase
import com.dandycat.examarch.adapter.SearchListAdapter
import com.dandycat.examarch.module.ToastModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideDi {

    @Singleton
    @Provides
    fun provideUserUseCase(repository: SearchRepository) = SearchUseCase(repository)

    @Singleton
    @Provides
    fun provideToastModule(@ApplicationContext context : Context) = ToastModule(context)
}