package com.nikita.popularmovies.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.nikita.popularmovies.common.models.MoviePreview

class MovieDetailsViewModel : ViewModel() {
  val movieDetailsLiveData = MutableLiveData<MovieDetailsScreen>()

  fun onInitialData(moviePreview: MoviePreview) {

  }
}