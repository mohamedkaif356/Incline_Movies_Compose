package com.example.inclinemoviescompose.data.datasource.local.moviesandtvshows

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inclinemoviescompose.domain.model.moviesandtvshows.MoviesAndTVShowsResult
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesAndTVShowsDao {

    @Query("SELECT * from movies_and_tv_shows_table")
    fun getMoviesAndTVShows(): Flow<List<MoviesAndTVShowsResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesAndTVShows(moviesAndTVShowsResult: List<MoviesAndTVShowsResult>)

    @Query("SELECT * FROM movies_and_tv_shows_table WHERE id = :id")
    suspend fun getUserByID(id: String): MoviesAndTVShowsResult

    @Query("DELETE FROM movies_and_tv_shows_table")
    suspend fun deleteAllRestaurants()

    @Query("DELETE FROM movies_and_tv_shows_table WHERE id = :id")
    suspend fun deleteById(id: String)

}