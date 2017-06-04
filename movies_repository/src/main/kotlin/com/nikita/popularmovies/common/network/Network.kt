package com.nikita.popularmovies.common.network

import com.squareup.moshi.JsonReader
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response

fun <T> Call<T>.executeUnsafe(): T {
  val response = execute()
  if (response.isSuccessful) {
    return response.body()!!
  } else {
    throw extractError(response)
  }
}

private fun <T> extractError(response: Response<T>): Exception {
  val httpException = HttpException(response)
  val reader = JsonReader.of(response.errorBody()!!.source())
  reader.beginObject()
  while (reader.hasNext()) {
    val name = reader.nextName()
    if (name == "status_message") {
      return ApiError(reader.nextString(), httpException)
    }
  }
  reader.endObject()
  return httpException
}

class ApiError(message: String, causeException: Throwable? = null) : RuntimeException(message, causeException)

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
val String.posterPathUrl: String get() = "$IMAGE_BASE_URL/w500$this"