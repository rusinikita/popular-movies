package com.nikita.popularmovies.details

import com.nikita.popularmovies.common.models.MovieDetails

data class MovieDetailsScreen(val isLoading: Boolean, val error: Throwable?, val content: MovieDetails)