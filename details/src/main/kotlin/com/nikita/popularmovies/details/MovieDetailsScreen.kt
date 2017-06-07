package com.nikita.popularmovies.details

import android.support.annotation.StringRes
import com.nikita.popularmovies.common.models.MovieDetails

data class MovieDetailsScreen(val isLoading: Boolean, val error: Throwable? = null, val content: MovieDetails, @StringRes val message: Int = -1)