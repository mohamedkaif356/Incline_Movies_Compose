package com.example.wsaudiology.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.wsaudiology.data.datasource.local.MoviesAndTVShowsDatabase
import com.example.wsaudiology.data.datasource.local.moviesandtvshows.MoviesAndTVShowsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideMyDatabase(@ApplicationContext context: Context): MoviesAndTVShowsDatabase {
        return Room.databaseBuilder(
            context, MoviesAndTVShowsDatabase::class.java, "my_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMyDao(moviesAndTVShowsDatabase: MoviesAndTVShowsDatabase): MoviesAndTVShowsDao {
        return moviesAndTVShowsDatabase.moviesAndTVShowDao()
    }
}