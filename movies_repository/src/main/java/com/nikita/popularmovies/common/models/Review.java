package com.nikita.popularmovies.common.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = MoviePreview.class, parentColumns = "id", childColumns = "movie_id"),
  indices = {@Index(value = "movie_id")})
public final class Review {
  @PrimaryKey
  public final String id;
  @ColumnInfo(name = "movie_id")
  public final String movieId;
  public final String author;
  public final String content;
  public final String url;

  public Review(String id, String movieId, String author, String content, String url) {
    this.id = id;
    this.movieId = movieId;
    this.author = author;
    this.content = content;
    this.url = url;
  }

  public Review withMovieId(String movieId) {
    return new Review(id, movieId, author, content, url);
  }
}