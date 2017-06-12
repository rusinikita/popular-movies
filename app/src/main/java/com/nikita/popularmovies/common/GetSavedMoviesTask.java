package com.nikita.popularmovies.common;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.nikita.popularmovies.common.database.MoviesContract;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.network.NetworkClasses;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class GetSavedMoviesTask extends AsyncTask<NetworkClasses.DataCallback<List<MoviePreview>>, Object, NetworkClasses.FetchResult<List<MoviePreview>>> {
  private List<NetworkClasses.DataCallback<List<MoviePreview>>> callbacks = new LinkedList<>();
  private ContentResolver contentResolver;

  public GetSavedMoviesTask(Context context) {
    contentResolver = context.getContentResolver();
  }

  @Override
  protected NetworkClasses.FetchResult<List<MoviePreview>> doInBackground(NetworkClasses.DataCallback<List<MoviePreview>>... params) {
    callbacks.clear();
    callbacks.addAll(Arrays.asList(params));
    try {
      Cursor cursor = contentResolver.query(MoviesContract.MovieEntity.CONTENT_URI,
        null,
        null,
        null,
        null);

      List<MoviePreview> moviePreviews = MoviesContract.MovieEntity.getPreviewsFromCrsor(cursor);

      return new NetworkClasses.FetchResult<>(moviePreviews, null);
    } catch (Throwable throwable) {
      return new NetworkClasses.FetchResult<>(null, throwable);
    }
  }

  @Override
  protected void onPostExecute(NetworkClasses.FetchResult<List<MoviePreview>> listFetchResult) {
    for (NetworkClasses.DataCallback<List<MoviePreview>> callback : callbacks) {
      callback.onResult(listFetchResult);
    }

    super.onPostExecute(listFetchResult);
  }
}
