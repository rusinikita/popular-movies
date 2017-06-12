package com.nikita.popularmovies.common.models;

import android.os.Parcelable;

public interface Poster extends Parcelable {
  String id();
  String title();
  String posterPath();
  @Section String section();
}
