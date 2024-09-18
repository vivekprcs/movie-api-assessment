package com.vivek.swiggy.di

import com.vivek.swiggy.data.ApiService
import com.vivek.swiggy.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val  Base_Url = "https://www.omdbapi.com/"

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    fun providesRepository(apiService: ApiService): MovieRepository {
        return MovieRepository(apiService)
    }

}