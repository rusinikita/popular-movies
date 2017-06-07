package com.nikita.popularmovies.common.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import java.util.Collections;
import java.util.List;

public final class MovieDetails {
  @Embedded
  public MoviePreview moviePreview;
  @Relation(parentColumn = "id", entityColumn = "movie_id")
  public List<Video> videos;
  @Relation(parentColumn = "id", entityColumn = "movie_id")
  public List<Review> reviews;
  @Ignore
  public boolean isSaved = false;

  public MovieDetails() {}

  @Ignore
  public MovieDetails(MoviePreview moviePreview) {
    this.moviePreview = moviePreview;
    videos = Collections.emptyList();
    reviews = Collections.emptyList();
  }

  @Ignore
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
