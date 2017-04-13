package com.nikita.pupularmoviesfirststage.common.network;


import android.net.Uri;
import android.support.annotation.StringDef;

import com.nikita.pupularmoviesfirststage.BuildConfig;
import com.nikita.pupularmoviesfirststage.common.models.Movie;
import com.nikita.pupularmoviesfirststage.common.models.PageResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Request {
  private static final String BASE_URL = "https://api.themoviedb.org/3/";
  private static final String MOVIE = "movie/";
  private static final String API_KEY = "api_key";

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

  public void movieList(@MovieTopic String topic, Network.DataCallback<PageResponse<Movie>> callback) {
    Uri buildUri = Uri.parse(BASE_URL).buildUpon()
      .appendPath(MOVIE)
      .appendPath(topic)
      .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
      .build();

    new Network.FetchDataTask<>(buildUri, new Parser.MovieList()).execute(callback);
  }
}
