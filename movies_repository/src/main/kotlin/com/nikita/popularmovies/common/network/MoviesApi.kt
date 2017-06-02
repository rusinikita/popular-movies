package com.nikita.popularmovies.common.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {
  @GET("movie/{movie_id}/videos")
  fun getVideos(@Path("movie_id") movieId: String)

  @GET("movie/{movie_id}/reviews")
  fun getReviews(@Path("movie_id") movieId: String)
}