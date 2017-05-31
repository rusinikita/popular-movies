package com.nikita.pupularmovies.common.models;

import com.nikita.pupularmovies.common.network.Request;

public interface Poster {
  String id();
  String title();
  String posterPath();
  @Request.Section String section();
}
