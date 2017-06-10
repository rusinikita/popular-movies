package com.nikita.popularmovies.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.nikita.popularmovies.common.MovieRepository;
import com.nikita.popularmovies.common.MovieRepositoryFactory;
import com.nikita.popularmovies.common.models.MoviePreview;

class MovieDetailsViewModel extends ViewModel {
  LiveData<MovieDetailsState> movieDetailsStateLiveData = new MutableLiveData<>();
  private final MovieRepository movieRepository;

  public MovieDetailsViewModel(MoviePreview initialMoviePreview, MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public void onFavoriteClick() {

  }

  static class Factory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    private final MoviePreview preview;

    public Factory(Context context, MoviePreview preview) {
      this.context = context.getApplicationContext();
      this.preview = preview;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
      if (modelClass == MovieDetailsViewModel.class) {
        return (T) new MovieDetailsViewModel(preview, MovieRepositoryFactory.create(context));
      } else {
        throw new IllegalArgumentException();
      }
    }
  }
}