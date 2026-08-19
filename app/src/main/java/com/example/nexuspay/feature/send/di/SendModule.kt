package com.example.nexuspay.feature.send.di

import com.example.nexuspay.feature.send.data.remote.ISendRemoteDataSource
import com.example.nexuspay.feature.send.data.remote.SendApi
import com.example.nexuspay.feature.send.data.remote.SendRemoteDataSource
import com.example.nexuspay.feature.send.data.repository.SendRepository
import com.example.nexuspay.feature.send.domain.repository.ISendRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SendModule {

    @Provides
    @Singleton
    fun provideSendApi(retrofit: Retrofit): SendApi = retrofit.create(SendApi::class.java)

    @Provides
    @Singleton
    fun provideSendRemoteDataSource(api: SendApi): ISendRemoteDataSource =
        SendRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideSendRepository(dataSource: ISendRemoteDataSource): ISendRepository =
        SendRepository(dataSource)
}