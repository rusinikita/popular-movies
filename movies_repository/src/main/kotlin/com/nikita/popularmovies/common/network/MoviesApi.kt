package com.nikita.popularmovies.common.network

import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.models.Review
import com.nikita.popularmovies.common.models.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {
  @GET("movie/{movie_id}")
  fun getMovie(@Path("movie_id") movieId: String): Call<MoviePreview>

  @GET("movie/{movie_id}/videos")
  fun getVideos(@Path("movie_id") movieId: String): Call<List<Video>>

  @GET("movie/{movie_id}/reviews")
  fun getReviews(@Path("movie_id") movieId: String): Call<List<Review>>
}