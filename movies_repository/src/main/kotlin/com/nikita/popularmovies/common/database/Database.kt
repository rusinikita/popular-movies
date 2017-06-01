package com.nikita.popularmovies.common.database

import android.arch.persistence.room.*
import android.arch.persistence.room.Database
import com.nikita.popularmovies.common.models.MovieDetails
import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.models.Review
import com.nikita.popularmovies.common.models.Video

@Dao
interface MovieDao {
  @Query("SELECT * FROM movie")
  fun getAll(): List<MovieDetails>

  @Insert
  fun insertPreview(movie: MoviePreview)

  @Insert
  fun insertVideos(videos: List<Video>)

  @Insert
  fun insertReviews(reviews: List<Review>)
}

@Database(entities = arrayOf(MoviePreview::class, Video::class, Review::class), version = 1)
abstract class Database : RoomDatabase() {
  abstract fun movieDao(): MovieDao;
}