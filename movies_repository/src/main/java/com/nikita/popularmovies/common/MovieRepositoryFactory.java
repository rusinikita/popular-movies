package com.nikita.popularmovies.common;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.nikita.popularmovies.common.database.MovieDao;
import com.nikita.popularmovies.common.database.PopularMoviesDatabase;
import com.nikita.popularmovies.common.network.MoviesApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class MovieRepositoryFactory {
  public static MovieRepository create(Context context) {
    Application application = (Application) context.getApplicationContext();
    MovieDao db = Room.databaseBuilder(context, PopularMoviesDatabase.class, "database")
      .build()
      .movieDao();
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
    return new MovieRepositoryImpl(db, api);

  }
}
