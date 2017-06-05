package com.nikita.popularmovies.common;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.network.Network;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class GetSavedMoviesTask extends AsyncTask<Network.DataCallback<List<MoviePreview>>, Object, Network.FetchResult<List<MoviePreview>>> {
  private List<Network.DataCallback<List<MoviePreview>>> callbacks = new LinkedList<>();
  private MovieRepository movieRepository;

  public GetSavedMoviesTask(Context context) {
    movieRepository = MoviesRepositoryKt.createMovieRepository((Application) context.getApplicationContext());
  }

  @Override
  protected Network.FetchResult<List<MoviePreview>> doInBackground(Network.DataCallback<List<MoviePreview>>... params) {
    callbacks.clear();
    callbacks.addAll(Arrays.asList(params));
    try {
      return new Network.FetchResult<>(movieRepository.getFavoriteMovies(), null);
    } catch (Throwable throwable) {
      return new Network.FetchResult<>(null, throwable);
    }
  }

  @Override
  protected void onPostExecute(Network.FetchResult<List<MoviePreview>> listFetchResult) {
    for (Network.DataCallback<List<MoviePreview>> callback : callbacks) {
      callback.onResult(listFetchResult);
    }

    super.onPostExecute(listFetchResult);
  }
}
