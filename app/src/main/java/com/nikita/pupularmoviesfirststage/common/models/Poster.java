package com.nikita.pupularmoviesfirststage.common.models;

import com.nikita.pupularmoviesfirststage.common.network.Request;

public interface Poster {
  String id();
  String title();
  String posterPath();
  @Request.Section String section();
}
