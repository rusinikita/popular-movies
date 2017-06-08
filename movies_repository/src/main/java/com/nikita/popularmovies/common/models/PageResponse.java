package com.nikita.popularmovies.common.models;


import com.squareup.moshi.Json;

import java.util.List;

public final class PageResponse<RESULT> {
  public final int page;
  public final List<RESULT> results;
  @Json(name = "total_pages")
  public final int totalPages;
  @Json(name = "total_results")
  public final int totalResults;

  public PageResponse(int page, List<RESULT> results, int totalPages, int totalResults) {
    this.page = page;
    this.results = results;
    this.totalPages = totalPages;
    this.totalResults = totalResults;
  }
}