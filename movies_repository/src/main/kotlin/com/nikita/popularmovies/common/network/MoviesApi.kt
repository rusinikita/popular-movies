package com.nikita.popularmovies.common.network

import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.models.PageResponse
import com.nikita.popularmovies.common.models.Review
import com.nikita.popularmovies.common.models.Video
import com.nikita.popularmovies.movies_repository.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {
  @GET("movie/{movie_id}")
  fun getMovie(@Path("movie_id") movieId: String): Call<MoviePreview>

  @GET("movie/{movie_id}/videos?api_key=${BuildConfig.API_KEY}")
  fun getVideos(@Path("movie_id") movieId: String): Call<PageResponse<Video>>

  @GET("movie/{movie_id}/reviews?api_key=${BuildConfig.API_KEY}")
  fun getReviews(@Path("movie_id") movieId: String): Call<PageResponse<Review>>
}