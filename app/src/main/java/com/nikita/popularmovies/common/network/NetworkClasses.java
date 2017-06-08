package com.nikita.popularmovies.common.network;

import android.accounts.NetworkErrorException;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public final class NetworkClasses {

  public interface Parser<DATA> {
    DATA parse(JSONObject object) throws JSONException;
  }

  public interface DataCallback<DATA> {
    void onResult(FetchResult<DATA> result);
  }

  public static final class FetchResult<DATA> {
    public final DATA data;
    public final Throwable error;

    public FetchResult(DATA data, Throwable error) {
      this.data = data;
      this.error = error;
    }
  }

  public static final class FetchDataTask<DATA> extends AsyncTask<DataCallback<DATA>, Object, FetchResult<DATA>> {
    private final Uri uri;
    private final Parser<DATA> parser;
    private List<DataCallback<DATA>> callbacks = new LinkedList<>();

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
      callbacks.clear();
      callbacks.addAll(Arrays.asList(params));

      try {
        URL url = new URL(uri.toString());
        InputStream in = url.openConnection().getInputStream();
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        if (scanner.hasNext()) {
          String jsonData = scanner.next();
          DATA data = parser.parse(new JSONObject(jsonData));

          return new FetchResult<>(data, null);
        } else {
          return new FetchResult<>(null, new NetworkErrorException("No data in response"));
        }
      } catch (Exception e) {
        return new FetchResult<>(null, e);
      }
    }

    @Override
    protected void onPostExecute(FetchResult<DATA> data) {
      for (DataCallback<DATA> callback : callbacks) {
        callback.onResult(data);
      }
      super.onPostExecute(data);
    }
  }
}
