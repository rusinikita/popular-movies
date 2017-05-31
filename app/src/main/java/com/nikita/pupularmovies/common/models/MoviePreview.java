package com.nikita.pupularmovies.common.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.nikita.pupularmovies.common.network.Request;

public final class MoviePreview implements Poster, Parcelable {
  public final String id;
  public final String title;
  public final String posterPath;
  public final String backdropPath;
  public final String overview;
  public final String releaseDate;
  public final double popularity;
  public final int voteCount;
  public final double voteAverage;


  public MoviePreview(String id,
                      String title,
                      String posterPath,
                      String backdropPath,
                      String overview,
                      String releaseDate,
                      double popularity,
                      int voteCount,
                      double voteAverage) {
    this.id = id;
    this.title = title;
    this.posterPath = posterPath;
    this.backdropPath = backdropPath;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.popularity = popularity;
    this.voteCount = voteCount;
    this.voteAverage = voteAverage;
  }

  public static final Creator<MoviePreview> CREATOR = new Creator<MoviePreview>() {
    @Override
    public MoviePreview createFromParcel(Parcel in) {
      String id = in.readString();
      String title = in.readString();
      String posterPath = in.readString();
      String backdropPath = in.readString();
      String overview = in.readString();
      String releaseDate = in.readString();
      double popularity = in.readDouble();
      int voteCount = in.readInt();
      double voteAverage = in.readDouble();
      return new MoviePreview(id, title, posterPath, backdropPath, overview, releaseDate, popularity, voteCount, voteAverage);
    }

    @Override
    public MoviePreview[] newArray(int size) {
      return new MoviePreview[size];
    }
  };

  @Override
  public String id() {
    return id;
  }

  @Override
  public String title() {
    return title;
  }

  @Override
  public String posterPath() {
    return posterPath;
  }

  @Override
  public String section() {
    return Request.MOVIE;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(title);
    dest.writeString(posterPath);
    dest.writeString(backdropPath);
    dest.writeString(overview);
    dest.writeString(releaseDate);
    dest.writeDouble(popularity);
    dest.writeInt(voteCount);
    dest.writeDouble(voteAverage);
  }
}
