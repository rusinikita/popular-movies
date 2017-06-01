package com.nikita.popularmovies.common.models;

public interface Poster {
  String id();
  String title();
  String posterPath();
  @Section String section();
}
