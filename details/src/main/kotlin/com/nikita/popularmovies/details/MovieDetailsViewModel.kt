package com.nikita.popularmovies.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.nikita.popularmovies.common.MovieRepository
import com.nikita.popularmovies.common.MovieRepositoryFabrica
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
      return MovieDetailsViewModel(preview, MovieRepositoryFabrica.create(context)) as T
    } else {
      throw IllegalArgumentException()
    }
  }
}

class MovieDetailsViewModel(initialMoviePreview: MoviePreview,
                            private val moviesRepository: MovieRepository) : ViewModel() {
  val movieDetailsLiveData = MutableLiveData<MovieDetailsState>()

  init {
    val initialScreenData = MovieDetailsState
      .ofContent(MovieDetails(initialMoviePreview))
      .withLoading(true)
    movieDetailsLiveData.postValue(initialScreenData)

    launch(UI) {
      val movieDetailsScreen = try {
        async(CommonPool) { moviesRepository.getMovieDetails(initialMoviePreview) }
          .await()
          .let { movieDetails -> MovieDetailsState.ofContent(movieDetails) }
      } catch (e: Throwable) {
        e.printStackTrace()
        initialScreenData
          .withError(e)
          .withLoading(false)
      }

      movieDetailsLiveData.postValue(movieDetailsScreen)
    }
  }

  fun onFavoriteClick() {
    launch(UI) {
      val value = movieDetailsLiveData.value!!
      val screen = try {
        val (movie, message) = async(CommonPool) {
          value.let {
            print(it)
            if (!it.content.isSaved) {
              moviesRepository.saveMovie(it.content) to R.string.movie_saved
            } else {
              moviesRepository.deleteMovie(it.content) to R.string.movie_removed
            }
          }
        }.await()

        MovieDetailsState
          .ofContent(movie)
          .withMessage(message)
      } catch (e: Throwable) {
        print(e)
        MovieDetailsState
          .ofContent(value.content)
          .withError(e)
      }

      movieDetailsLiveData.postValue(screen)
    }
  }
}