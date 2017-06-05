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
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

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
    val initialScreenData = MovieDetailsScreen(
      isLoading = true,
      error = null,
      content = MovieDetails(initialMoviePreview))
    movieDetailsLiveData.postValue(initialScreenData)

    launch(UI) {
      val movieDetailsScreen = try {
        async(CommonPool) { moviesRepository.getMovieDetails(initialMoviePreview) }
          .await()
          .let { movieDetails -> initialScreenData.copy(isLoading = false, content = movieDetails) }
      } catch (e: Throwable) {
        e.printStackTrace()
        initialScreenData.copy(isLoading = false, error = e)
      }

      movieDetailsLiveData.postValue(movieDetailsScreen)
    }
  }

  fun onFavoriteClick() {
    launch(UI) {
      async(CommonPool) {
        movieDetailsLiveData.value?.let {
          // TODO fix second click crash
          print(it)
          moviesRepository.saveMovie(it.content)
        }
      }.await()
    }
  }
}