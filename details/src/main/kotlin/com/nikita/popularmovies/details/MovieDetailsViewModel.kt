package com.nikita.popularmovies.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MovieDetailsViewModel : ViewModel() {
  val movieDetailsLiveData = MutableLiveData<MovieDetailsScreen>()
}