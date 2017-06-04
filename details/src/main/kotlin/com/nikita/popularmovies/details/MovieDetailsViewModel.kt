package com.nikita.popularmovies.details

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.nikita.popularmovies.common.MovieRepository
import com.nikita.popularmovies.common.createMovieRepository
import com.nikita.popularmovies.common.models.MovieDetails
import com.nikita.popularmovies.common.models.MoviePreview

class MovieDetailsViewModelFactory(private val context: Context, private val preview: MoviePreview) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(klass: Class<T>): T {
    if (klass == MovieDetailsViewModel::class.java) {
      @Suppress("UNCHECKED_CAST")
      return MovieDetailsViewModel(preview, createMovieRepository(context.applicationContext as Application)) as T
    } else {
      throw IllegalArgumentException()
    }
  }
}

class MovieDetailsViewModel(initialMoviePreview: MoviePreview,
                            private val moviesRepository: MovieRepository) : ViewModel() {
  val movieDetailsLiveData = MutableLiveData<MovieDetailsScreen>()

  init {
    movieDetailsLiveData.postValue(MovieDetailsScreen(
      isLoading = true,
      error = null,
      content = MovieDetails(initialMoviePreview)
    ))
  }
}