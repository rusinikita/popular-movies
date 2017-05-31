package com.nikita.popularmovies.common.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Collections;
import java.util.List;

@Entity
public final class MovieDetails {
  @PrimaryKey
  private final String id;
  @Embedded
  private final MoviePreview moviePreview;
  private final List<Video> videos;
  private final List<Review> reviews;

  public MovieDetails(MoviePreview moviePreview) {
    this.id = moviePreview.id;
    this.moviePreview = moviePreview;
    videos = Collections.emptyList();
    reviews = Collections.emptyList();
  }

  public MovieDetails(MoviePreview moviePreview, List<Video> videos, List<Review> reviews) {
    this.id = moviePreview.id;
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
