package com.nikita.popularmovies.common.network;

import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.PageResponse;
import com.nikita.popularmovies.common.models.Review;
import com.nikita.popularmovies.common.models.Video;
import com.nikita.popularmovies.movies_repository.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesApi {
  @GET("movie/{movie_id}")
  Call<MoviePreview> getMovie(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/videos?api_key=" + BuildConfig.API_KEY)
  Call<PageResponse<Video>> getVideos(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/reviews?api_key=" + BuildConfig.API_KEY)
  Call<PageResponse<Review>> getReviews(@Path("movie_id") String movieId);
}