package com.nikita.pupularmovies.common.network;


import android.net.Uri;
import android.support.annotation.StringDef;

import com.nikita.pupularmovies.BuildConfig;
import com.nikita.pupularmovies.common.models.MoviePreview;
import com.nikita.pupularmovies.common.models.PageResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public final class Request {
  private static final String BASE_URL = "https://api.themoviedb.org/3/";
  private static final String API_KEY = "api_key";
  public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({
    MOVIE,
    TV_SHOW,
    PERSON
  })
  public @interface Section {}
  public static final String MOVIE = "movie";
  public static final String TV_SHOW = "tv";
  public static final String PERSON = "person";

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({
    POPULAR,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING
  })
  public @interface MovieTopic {}
  public static final String POPULAR = "popular";
  public static final String TOP_RATED = "top_rated";
  public static final String UPCOMING = "upcoming";
  public static final String NOW_PLAYING = "now_playing";
  public static List<String> topics() {
    return Arrays.asList(POPULAR, TOP_RATED, UPCOMING, NOW_PLAYING);
  }

  public static void movieList(@MovieTopic String topic, Network.DataCallback<PageResponse<MoviePreview>> callback) {
    Uri buildUri = Uri.parse(BASE_URL).buildUpon()
      .appendPath(MOVIE)
      .appendPath(topic)
      .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
      .build();

    new Network.FetchDataTask<>(buildUri, new Parser.MovieList()).execute(callback);
  }

  public static String posterImageUrl(String posterPath) {
    return IMAGE_BASE_URL + "/w500" + posterPath;
  }
}
