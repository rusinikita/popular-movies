package com.nikita.popularmovies.common.models;

import com.nikita.popularmovies.common.network.Request;

public interface Poster {
  String id();
  String title();
  String posterPath();
  @Request.Section String section();
}
