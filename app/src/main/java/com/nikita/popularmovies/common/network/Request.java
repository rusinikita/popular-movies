package com.nikita.popularmovies.common.network;


import android.net.Uri;
import android.support.annotation.StringDef;

import com.nikita.popularmovies.BuildConfig;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.PageResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

import static com.nikita.popularmovies.common.models.SectionKt.MOVIE;

public final class Request {
  private static final String BASE_URL = "https://api.themoviedb.org/3/";
  private static final String API_KEY = "api_key";

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({
    SAVED,
    POPULAR,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING
  })
  public @interface MovieTopic {}
  public static final String SAVED = "saved";
  public static final String POPULAR = "popular";
  public static final String TOP_RATED = "top_rated";
  public static final String UPCOMING = "upcoming";
  public static final String NOW_PLAYING = "now_playing";
  public static List<String> topics() {
    return Arrays.asList(SAVED, POPULAR, TOP_RATED, UPCOMING, NOW_PLAYING);
  }

  public static void movieList(@MovieTopic String topic, Network.DataCallback<PageResponse<MoviePreview>> callback) {
    Uri buildUri = Uri.parse(BASE_URL).buildUpon()
      .appendPath(MOVIE)
      .appendPath(topic)
      .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
      .build();

    new Network.FetchDataTask<>(buildUri, new Parser.MovieList()).execute(callback);
  }
}
