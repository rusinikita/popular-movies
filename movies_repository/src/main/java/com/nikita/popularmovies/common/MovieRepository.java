package com.nikita.popularmovies.common;

import com.nikita.popularmovies.common.models.MovieDetails;
import com.nikita.popularmovies.common.models.MoviePreview;

import java.util.List;

public interface MovieRepository {
  List<MoviePreview> getFavoriteMovies();
  MovieDetails saveMovie(MovieDetails movie);
  MovieDetails deleteMovie(MovieDetails movie);
  MovieDetails getMovieDetails(MoviePreview moviePreview) throws Exception;
}

