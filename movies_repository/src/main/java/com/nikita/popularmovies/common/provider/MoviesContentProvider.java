package com.nikita.popularmovies.common.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nikita.popularmovies.common.database.MoviesContract;
import com.nikita.popularmovies.common.database.MoviesDbHelper;

public final class MoviesContentProvider extends ContentProvider {
  private MoviesDbHelper dbHelper;
  private static final UriMatcher matcher;
  private static final int MOVIES = 100;

  static {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_MOVIES, MOVIES);
    matcher = uriMatcher;
  }

  @Override
  public boolean onCreate() {
    dbHelper = new MoviesDbHelper(getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                      @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    int match = matcher.match(uri);

    Cursor retCursor;

    switch (match) {
      case MOVIES:
        retCursor =  db.query(MoviesContract.MovieEntity.TABLE_NAME,
          projection,
          selection,
          selectionArgs,
          null,
          null,
          sortOrder);
        break;
      // Default exception
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    retCursor.setNotificationUri(getContext().getContentResolver(), uri);

    return retCursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
