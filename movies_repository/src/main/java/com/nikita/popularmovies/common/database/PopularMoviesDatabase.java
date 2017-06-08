package com.nikita.popularmovies.common.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.Review;
import com.nikita.popularmovies.common.models.Video;

@Database(entities = {MoviePreview.class, Video.class, Review.class}, version = 1)
public abstract class PopularMoviesDatabase extends RoomDatabase {
  public abstract MovieDao movieDao();
}
