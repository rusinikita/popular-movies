package com.nikita.pupularmoviesfirststage.common.models;


import com.nikita.pupularmoviesfirststage.common.network.Request;

import java.util.List;

public final class MoviePreview implements Poster {
  public final String id;
  public final String title;
  public final String posterPath;
  public final String backdropPath;
  public final String overview;
  public final String releaseDate;
  public final List<Integer> genres;
  public final Double popularity;
  public final int voteCount;
  public final Double voteAverage;


  public MoviePreview(String id,
                      String title,
                      String posterPath,
                      String backdropPath,
                      String overview,
                      String releaseDate,
                      List<Integer> genres,
                      Double popularity,
                      int voteCount,
                      Double voteAverage) {
    this.id = id;
    this.title = title;
    this.posterPath = posterPath;
    this.backdropPath = backdropPath;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.genres = genres;
    this.popularity = popularity;
    this.voteCount = voteCount;
    this.voteAverage = voteAverage;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public String title() {
    return title;
  }

  @Override
  public String posterPath() {
    return posterPath;
  }

  @Override
  public String section() {
    return Request.MOVIE;
  }
}
