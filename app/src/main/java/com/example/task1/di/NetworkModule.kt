package com.example.task1.di

import com.example.task1.retrofit.NetworkApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Provides
    fun provideApi(retrofit: Retrofit): NetworkApi =
        retrofit.create(NetworkApi::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofit(): Retrofit {
        val json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}