package com.nikita.popularmovies.common.models;

import java.util.Collections;
import java.util.List;

public final class MovieDetails {
  private final MoviePreview moviePreview;
  private final List<Video> videos;
  private final List<Review> reviews;

  public MovieDetails(MoviePreview moviePreview) {
    this.moviePreview = moviePreview;
    videos = Collections.emptyList();
    reviews = Collections.emptyList();
  }

  public MovieDetails(MoviePreview moviePreview, List<Video> videos, List<Review> reviews) {
    this.moviePreview = moviePreview;
    this.videos = videos;
    this.reviews = reviews;
  }

  public MoviePreview getMoviePreview() {
    return moviePreview;
  }

  public List<Video> getVideos() {
    return videos;
  }

  public List<Review> getReviews() {
    return reviews;
  }
}
