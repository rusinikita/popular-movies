package com.nikita.pupularmovies.common.network;


import com.nikita.pupularmovies.common.models.MoviePreview;
import com.nikita.pupularmovies.common.models.PageResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Parser {
  public static final class MovieList implements Network.Parser<PageResponse<MoviePreview>> {
    @Override
    public PageResponse<MoviePreview> parse(JSONObject object) throws JSONException {
      int page = object.getInt("page");
      int pages = object.getInt("total_pages");
      int results = object.getInt("total_results");

      JSONArray jsonMovies = object.getJSONArray("results");
      List<MoviePreview> movies = new ArrayList<>(jsonMovies.length());
      for (int i = 0; i < jsonMovies.length(); i++) {
        JSONObject movie = jsonMovies.getJSONObject(i);
        movies.add(new MoviePreview(
          movie.getString("id"),
          movie.getString("title"),
          movie.getString("poster_path"),
          movie.getString("backdrop_path"),
          movie.getString("overview"),
          movie.getString("release_date"),
          movie.getDouble("popularity"),
          movie.getInt("vote_count"),
          movie.getDouble("vote_average")));
      }

      return new PageResponse<>(page, movies, pages, results);
    }
  }
}
