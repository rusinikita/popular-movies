package com.nikita.popularmovies.common;

import android.app.Application;
import android.content.Context;

import com.nikita.popularmovies.common.database.MoviesDbHelper;
import com.nikita.popularmovies.common.network.MoviesApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class MovieRepositoryFactory {
  public static MovieRepository create(Context context) {
    Application application = (Application) context.getApplicationContext();

    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okHttp = new OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .build();
    MoviesApi api = new Retrofit.Builder()
      .client(okHttp)
      .baseUrl("https://api.themoviedb.org/3/")
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
      .create(MoviesApi.class);
    return new MovieRepositoryImpl(new MoviesDbHelper(application), api);

  }
}
