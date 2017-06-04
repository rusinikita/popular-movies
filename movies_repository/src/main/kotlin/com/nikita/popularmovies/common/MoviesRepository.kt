package com.nikita.popularmovies.common

import android.app.Application
import android.arch.persistence.room.Room
import com.nikita.popularmovies.common.database.Database
import com.nikita.popularmovies.common.database.MovieDao
import com.nikita.popularmovies.common.models.MovieDetails
import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.network.MoviesApi
import com.nikita.popularmovies.common.network.executeUnsafe
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface MovieRepository {
  fun getFavoriteMovies(): List<MoviePreview>
  fun saveMovie(movie: MovieDetails)
  fun getMovieDetails(moviePreview: MoviePreview): MovieDetails
}

class MovieRepositoryImpl(private val moviesDao: MovieDao,
                          private val moviesService: MoviesApi): MovieRepository {

  override fun getFavoriteMovies(): List<MoviePreview> {
    return moviesDao.getAll()
  }

  override fun saveMovie(movie: MovieDetails) {
    moviesDao.insertPreview(movie.moviePreview)
    moviesDao.insertVideos(movie.videos)
    moviesDao.insertReviews(movie.reviews)
  }

  override fun getMovieDetails(moviePreview: MoviePreview): MovieDetails {
    val movieId = moviePreview.id
    var result = moviesDao.getMovie(movieId)
    if (result == null) {
      val videos = moviesService.getVideos(movieId).executeUnsafe()
      val reviews = moviesService.getReviews(movieId).executeUnsafe()
      result = MovieDetails(moviePreview, videos, reviews)
    }
    return result
  }
}

fun createMovieRepository(context: Application): MovieRepository {
  val db = Room.databaseBuilder(context, Database::class.java, "database")
    .build()
    .movieDao()
  val api = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(MoviesApi::class.java)
  return MovieRepositoryImpl(db, api)
}