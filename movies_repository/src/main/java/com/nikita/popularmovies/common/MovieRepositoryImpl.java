package com.nikita.popularmovies.common;

import com.nikita.popularmovies.common.database.MoviesDbHelper;
import com.nikita.popularmovies.common.models.MovieDetails;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.Review;
import com.nikita.popularmovies.common.models.Video;
import com.nikita.popularmovies.common.network.MoviesApi;
import com.nikita.popularmovies.common.network.Network;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

final class MovieRepositoryImpl implements MovieRepository {
  private MoviesDbHelper moviesDao;
  private MoviesApi moviesService;

  public MovieRepositoryImpl(MoviesDbHelper moviesDao, MoviesApi moviesService) {
    this.moviesDao = moviesDao;
    this.moviesService = moviesService;
  }

  @Override
  public List<MoviePreview> getFavoriteMovies() {
    return moviesDao.getAll();
  }

  @Override
  public MovieDetails saveMovie(MovieDetails movie) {
    moviesDao.saveMovie(movie.moviePreview);
    movie.isSaved = true;
    return movie;
  }

  @Override
  public MovieDetails deleteMovie(MovieDetails movie) {
    moviesDao.deleteMovie(movie.moviePreview);
    movie.isSaved = false;
    return movie;
  }

  @Override
  public MovieDetails getMovieDetails(MoviePreview moviePreview) throws Exception {
    String movieId = moviePreview.id;
    MovieDetails result;
    List<Video> videos = new LinkedList<>();
    for (Video v : Network.executeUnsafe(moviesService.getVideos(movieId)).results) {
      videos.add(v.withMovieId(movieId));
    }
    List<Review> reviews = new LinkedList<>();
    for (Review r : Network.executeUnsafe(moviesService.getReviews(movieId)).results) {
      reviews.add(r.withMovieId(movieId));
    }
    result = new MovieDetails(moviePreview, new ArrayList<>(videos), new ArrayList<>(reviews));

    return result;
  }
}