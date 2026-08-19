package com.example.nexuspay.feature.home.di

import com.example.nexuspay.feature.home.data.repository.HomeRepository
import com.example.nexuspay.core.local.UserDataStore
import com.example.nexuspay.feature.home.data.remote.HomeApi
import com.example.nexuspay.feature.home.data.remote.HomeRemoteDataSource
import com.example.nexuspay.feature.home.data.remote.IHomeRemoteDataSource
import com.example.nexuspay.feature.home.domain.repository.IHomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideHomeRemoteDataSource(api: HomeApi): IHomeRemoteDataSource =
        HomeRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideHomeRepository(
        dataSource: IHomeRemoteDataSource,
        userDataStore: UserDataStore,
    ): IHomeRepository = HomeRepository(dataSource, userDataStore)
}
