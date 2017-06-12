package com.nikita.popularmovies.common.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = "movie_id")})
public final class Video {
  @PrimaryKey
  public final String id;
  @ColumnInfo(name = "movie_id")
  public final String movieId;
  public final String key;
  public final String name;
  public final String site;
  public final String type;

  public Video(String id, String movieId, String key, String name, String site, String type) {
    this.id = id;
    this.movieId = movieId;
    this.key = key;
    this.name = name;
    this.site = site;
    this.type = type;
  }

  public Video withMovieId(String movieId) {
    return new Video(id, movieId, key, name, site, type);
  }
}