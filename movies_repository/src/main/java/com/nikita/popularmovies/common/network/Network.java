package com.nikita.popularmovies.common.network;


import com.squareup.moshi.JsonReader;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public final class Network {
  public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";

  public static String posterPathUrl(String posterPath) {
    return IMAGE_BASE_URL + "/w500" + posterPath;
  }

  public final static class ApiError extends RuntimeException {
    public ApiError(String message) {
      super(message);
    }

    public ApiError(String message, Throwable cause) {
      super(message, cause);
    }

    public ApiError(Throwable cause) {
      super(cause);
    }
  }

  public static <T> T executeUnsafe(Call<T> request) throws Exception {
    Response<T> response;
    response = request.execute();
    if (response.isSuccessful()) {
      return response.body();
    } else {
      throw extractError(response);
    }
  }

  public static <T> Exception extractError(Response<T> response) {
    HttpException httpException = new HttpException(response);
    JsonReader reader = JsonReader.of(response.errorBody().source());
    Exception exception = new ApiError(httpException);
    try {
      reader.beginObject();
      while (reader.hasNext()) {
        String name = reader.nextName();
        if (name.equals("status_message")) {
          exception = new ApiError(reader.nextString(), httpException);
        }
      }
    } catch (IOException ignored) {
    }

    return exception;
  }
}
