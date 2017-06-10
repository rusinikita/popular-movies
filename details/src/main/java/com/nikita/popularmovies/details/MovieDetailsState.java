package com.nikita.popularmovies.details;

import android.support.annotation.StringRes;

import com.nikita.popularmovies.common.models.MovieDetails;

public final class MovieDetailsState {
  public final Boolean isLoading;
  public final Throwable error;
  public final MovieDetails content;
  @StringRes
  public final int message;

  private MovieDetailsState(Boolean isLoading, Throwable error, MovieDetails content, int message) {
    this.isLoading = isLoading;
    this.error = error;
    this.content = content;
    this.message = message;
  }

  public static MovieDetailsState create(Boolean isLoading, Throwable error, MovieDetails content, int message) {
    return new MovieDetailsState(isLoading, error, content, message);
  }

  public static MovieDetailsState ofContent(MovieDetails movieDetails) {
    return create(false, null, movieDetails, -1);
  }

  public MovieDetailsState withLoading(boolean isLoading) {
    return create(isLoading, error, content, message);
  }

  public MovieDetailsState withError(Throwable error) {
    return create(isLoading, error, content, message);
  }

  public MovieDetailsState withMessage(@StringRes int message) {
    return create(isLoading, error, content, message);
  }
}