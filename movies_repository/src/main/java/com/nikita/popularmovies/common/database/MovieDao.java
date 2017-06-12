package com.nikita.popularmovies.common.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nikita.popularmovies.common.models.MovieDetails;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.Review;
import com.nikita.popularmovies.common.models.Video;

import java.util.List;

@Dao
public interface MovieDao {
  @Query("SELECT * FROM movie")
  List<MoviePreview> getAll();

  @Query("SELECT * FROM movie WHERE id LIKE :movieId")
  MovieDetails getMovie(String movieId);

  @Insert
  void insertPreview(MoviePreview movie);

  @Delete
  void deleteMovie(MoviePreview movie);

  @Delete
  void deleteReviews(List<Review> reviews);

  @Delete
  void deleteVideos(List<Video> videos);

  @Insert
  void insertVideos(List<Video> videos);

  @Insert
  void insertReviews(List<Review> reviews);
}
