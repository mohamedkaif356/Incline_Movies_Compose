package com.example.wsaudiology.presentation.di

import com.example.wsaudiology.data.datasource.remote.AuthInterceptor
import com.example.wsaudiology.data.datasource.remote.PropertyProvider
import com.example.wsaudiology.data.datasource.remote.moviesandtvshows.MoviesAndTVShowAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(propertyProvider: PropertyProvider): AuthInterceptor {
        return AuthInterceptor(propertyProvider)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(propertyProvider: PropertyProvider, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(propertyProvider.getProperty("BASE_URL")).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): MoviesAndTVShowAPI =
        retrofit.create(MoviesAndTVShowAPI::class.java)


}