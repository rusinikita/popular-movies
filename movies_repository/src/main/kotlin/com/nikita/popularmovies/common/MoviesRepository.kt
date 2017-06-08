package com.nikita.popularmovies.common

import android.app.Application
import android.arch.persistence.room.Room
import com.nikita.popularmovies.common.database.MovieDao
import com.nikita.popularmovies.common.database.PopularMoviesDatabase
import com.nikita.popularmovies.common.models.MovieDetails
import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.network.MoviesApi
import com.nikita.popularmovies.common.network.executeUnsafe
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface MovieRepository {
  fun getFavoriteMovies(): List<MoviePreview>
  fun saveMovie(movie: MovieDetails): MovieDetails
  fun deleteMovie(movie: MovieDetails): MovieDetails
  fun getMovieDetails(moviePreview: MoviePreview): MovieDetails
}

class MovieRepositoryImpl(private val moviesDao: MovieDao,
                          private val moviesService: MoviesApi): MovieRepository {

  override fun getFavoriteMovies(): List<MoviePreview> {
    return moviesDao.getAll()
  }

  override fun saveMovie(movie: MovieDetails): MovieDetails {
    moviesDao.insertPreview(movie.moviePreview)
    moviesDao.insertVideos(movie.videos)
    moviesDao.insertReviews(movie.reviews)
    movie.isSaved = true
    return movie
  }

  override fun deleteMovie(movie: MovieDetails): MovieDetails {
    // TODO fix deleting FOREIGN KEY constraint failed
    moviesDao.deleteMovie(movie.moviePreview)
    movie.isSaved = false
    return movie
  }

  override fun getMovieDetails(moviePreview: MoviePreview): MovieDetails {
    val movieId = moviePreview.id
    var result = moviesDao.getMovie(movieId)
    if (result == null) {
      val videos = moviesService.getVideos(movieId).executeUnsafe().results.map { it.copy(movieId = movieId) }
      val reviews = moviesService.getReviews(movieId).executeUnsafe().results.map { it.copy(movieId = movieId) }
      result = MovieDetails(moviePreview, videos, reviews)
    } else {
      result.isSaved = true
    }
    return result
  }
}

fun createMovieRepository(context: Application): MovieRepository {
  val db = Room.databaseBuilder(context, PopularMoviesDatabase::class.java, "database")
    .build()
    .movieDao()
  val okHttp = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()
  val api = Retrofit.Builder()
    .client(okHttp)
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(MoviesApi::class.java)
  return MovieRepositoryImpl(db, api)
}