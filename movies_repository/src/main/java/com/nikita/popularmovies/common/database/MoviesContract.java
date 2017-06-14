package com.nikita.popularmovies.common.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.nikita.popularmovies.common.models.MoviePreview;

import java.util.LinkedList;
import java.util.List;

public final class MoviesContract {
  public static final String AUTHORITY = "com.nikita.popularmovies";

  // The base content URI = "content://" + <authority>
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  // Define the possible paths for accessing data in this contract
  // This is the path for the "tasks" directory
  public static final String PATH_MOVIES = "movies";

  public static class MovieEntity implements BaseColumns {
    public static final Uri CONTENT_URI =
      BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


    // Task table and column names
    public static final String TABLE_NAME = "movie";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_VOTE_COUNT = "vote_count";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";

    public static List<MoviePreview> getPreviewsFromCrsor(Cursor cursor) {
      int id = cursor.getColumnIndex(COLUMN_ID);
      int title = cursor.getColumnIndex(COLUMN_TITLE);
      int postetPath = cursor.getColumnIndex(COLUMN_POSTER_PATH);
      int backdropPath = cursor.getColumnIndex(COLUMN_BACKDROP_PATH);
      int overview = cursor.getColumnIndex(COLUMN_OVERVIEW);
      int releaseDate = cursor.getColumnIndex(COLUMN_RELEASE_DATE);
      int popularity = cursor.getColumnIndex(COLUMN_POPULARITY);
      int voteCount = cursor.getColumnIndex(COLUMN_VOTE_COUNT);
      int voteAverage = cursor.getColumnIndex(COLUMN_VOTE_AVERAGE);

      List<MoviePreview> list = new LinkedList<>();
      while (cursor.moveToNext()) {
        list.add(new MoviePreview(
          cursor.getString(id),
          cursor.getString(title),
          cursor.getString(postetPath),
          cursor.getString(backdropPath),
          cursor.getString(overview),
          cursor.getString(releaseDate),
          cursor.getDouble(popularity),
          cursor.getInt(voteCount),
          cursor.getDouble(voteAverage)
        ));
      }

      return list;
    }

    public static ContentValues contentVlues(MoviePreview movie) {
      ContentValues cv = new ContentValues(9);
      cv.put(COLUMN_ID, movie.id);
      cv.put(COLUMN_TITLE, movie.title);
      cv.put(COLUMN_POSTER_PATH, movie.posterPath);
      cv.put(COLUMN_BACKDROP_PATH, movie.backdropPath);
      cv.put(COLUMN_OVERVIEW, movie.overview);
      cv.put(COLUMN_RELEASE_DATE, movie.releaseDate);
      cv.put(COLUMN_POPULARITY, movie.popularity);
      cv.put(COLUMN_VOTE_COUNT, movie.voteCount);
      cv.put(COLUMN_VOTE_AVERAGE, movie.voteAverage);
      return cv;
    }
  }
}
