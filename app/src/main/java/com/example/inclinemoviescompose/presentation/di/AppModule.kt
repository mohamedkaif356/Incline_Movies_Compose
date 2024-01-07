package com.example.inclinemoviescompose.presentation.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.example.inclinemoviescompose.data.datasource.remote.PropertyProvider
import com.example.inclinemoviescompose.utils.PropertyProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Provides
    @Singleton
    fun providePropertyProvider(assetManager: AssetManager): PropertyProvider {
        return PropertyProviderImpl(assetManager)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}