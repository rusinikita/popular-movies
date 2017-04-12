package com.nikita.pupularmoviesfirststage.common.network;


import android.support.annotation.StringDef;

import com.nikita.pupularmoviesfirststage.common.models.Movie;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public final class Request {
  public static final String BASE_URL = "https://api.themoviedb.org/3/";
  private static final String MOVIE = "movie/";

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({
    POPULAR,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING,
    LATEST
  })
  public @interface MovieTopic {}
  public static final String POPULAR = "popular";
  public static final String TOP_RATED = "top_rated";
  public static final String UPCOMING = "upcoming";
  public static final String NOW_PLAYING = "now_playing";
  public static final String LATEST = "latest";

  public void movieList(@MovieTopic String topic, Network.DataCallback<List<Movie>> callback) {

  }
}
