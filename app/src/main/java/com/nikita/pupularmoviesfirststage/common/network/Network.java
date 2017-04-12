package com.nikita.pupularmoviesfirststage.common.network;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class Network {

  interface Parser<DATA> {
    DATA parse(JSONObject object);
  }

  interface DataCallback<DATA> {
    void onResult(FetchResult<DATA> result);
  }

  final class FetchResult<DATA> {
    public final DATA data;
    public final Throwable error;

    public FetchResult(DATA data, Throwable error) {
      this.data = data;
      this.error = error;
    }
  }

  public final class FetchDataTask<DATA> extends AsyncTask<DataCallback<DATA>, Object, FetchResult<DATA>> {
    private final Uri uri;
    private final Parser<DATA> parser;
    private List<DataCallback<DATA>> callbacks = Collections.emptyList();

    public FetchDataTask(Uri uri, Parser<DATA> parser) {
      this.uri = uri;
      this.parser = parser;
    }

    @SafeVarargs
    @Override
    protected final FetchResult<DATA> doInBackground(final DataCallback<DATA>... params) {
      if (params.length == 0) {
        return new FetchResult<>(null, new IllegalArgumentException("No callbacks"));
      }
      callbacks.addAll(Arrays.asList(params));

      try {
        return new FetchResult<>(null, new IllegalArgumentException("No callbacks"));
      } catch (Exception e) {
        return new FetchResult<>(null, e);
      }
    }

    @Override
    protected void onPostExecute(FetchResult<DATA> data) {
      super.onPostExecute(data);
    }
  }
}
