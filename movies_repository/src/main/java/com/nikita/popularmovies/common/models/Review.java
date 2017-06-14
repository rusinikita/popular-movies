package com.nikita.popularmovies.common.models;

public final class Review {
  public final String id;
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