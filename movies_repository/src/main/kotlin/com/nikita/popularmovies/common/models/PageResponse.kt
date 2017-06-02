package com.nikita.popularmovies.common.models

import com.squareup.moshi.Json

data class PageResponse<out RESULT>(
  val page: Int,
  val results: List<RESULT>,
  @Json(name = "total_pages") val totalPages: Int,
  @Json(name = "total_results") val totalResults: Int)