package com.nikita.pupularmovies.common.models;


import java.util.List;

public final class PageResponse<RESULT> {
  public final int page;
  public final List<RESULT> results;
  public final int totalPages;
  public final int totalResults;

  public PageResponse(int page, List<RESULT> results, int totalPages, int totalResults) {
    this.page = page;
    this.results = results;
    this.totalPages = totalPages;
    this.totalResults = totalResults;
  }
}
