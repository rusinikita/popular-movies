package com.nikita.popularmovies.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nikita.popularmovies.common.models.MoviePreview;

import java.util.List;

import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_BACKDROP_PATH;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_ID;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_OVERVIEW;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_POPULARITY;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_POSTER_PATH;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_RELEASE_DATE;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_TITLE;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_VOTE_AVERAGE;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.COLUMN_VOTE_COUNT;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.TABLE_NAME;
import static com.nikita.popularmovies.common.database.MoviesContract.MovieEntity.contentVlues;

public final class MoviesDbHelper extends SQLiteOpenHelper {
  public MoviesDbHelper(Context context) {
    super(context, "database", null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
      "(" +
      COLUMN_ID + " TEXT PRIMARY KEY, " +
      COLUMN_TITLE + " TEXT, " +
      COLUMN_POSTER_PATH + " TEXT, " +
      COLUMN_BACKDROP_PATH + " TEXT, " +
      COLUMN_OVERVIEW + " TEXT, " +
      COLUMN_RELEASE_DATE + " TEXT, " +
      COLUMN_POPULARITY + " REAL, " +
      COLUMN_VOTE_COUNT + " INTEGER, " +
      COLUMN_VOTE_AVERAGE + " REAL" +
      ")");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
      // Simplest implementation is to drop all old tables and recreate them
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
    }
  }

  public List<MoviePreview> getAll() {
    throw new UnsupportedOperationException();
  }

  public void saveMovie(MoviePreview movie) {
    SQLiteDatabase db = getWritableDatabase();
    db.beginTransaction();
    try {
      db.insertWithOnConflict(
        TABLE_NAME,
        null,
        contentVlues(movie),
        SQLiteDatabase.CONFLICT_REPLACE);
      db.setTransactionSuccessful();
    } catch (Exception ignored) {

    } finally {
      db.endTransaction();
    }
  }

  public void deleteMovie(MoviePreview movie) {
    SQLiteDatabase db = getWritableDatabase();
    db.beginTransaction();
    try {
      db.delete(
        TABLE_NAME,
        COLUMN_ID + "=" + movie.id(),
        null);
      db.setTransactionSuccessful();
    } catch (Exception ignored) {

    } finally {
      db.endTransaction();
    }
  }
}
