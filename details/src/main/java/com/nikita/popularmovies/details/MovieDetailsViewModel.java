package com.nikita.popularmovies.details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Process;

import com.nikita.popularmovies.common.MovieRepository;
import com.nikita.popularmovies.common.MovieRepositoryFactory;
import com.nikita.popularmovies.common.models.MovieDetails;
import com.nikita.popularmovies.common.models.MoviePreview;

class MovieDetailsViewModel extends ViewModel {
  MutableLiveData<MovieDetailsState> movieDetailsStateLiveData = new MutableLiveData<>();
  private final MovieRepository movieRepository;
  private Thread executingThread = null;

  public MovieDetailsViewModel(final MoviePreview initialMoviePreview, final MovieRepository movieRepository) {
    this.movieRepository = movieRepository;

    final MovieDetailsState initialState = MovieDetailsState
      .ofContent(new MovieDetails(initialMoviePreview))
      .withLoading(true);
    movieDetailsStateLiveData.postValue(initialState);

    executingThread = new Thread() {
      @Override
      public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        MovieDetailsState movieDetailsState;
        try {
          MovieDetails movieDetails = movieRepository.getMovieDetails(initialMoviePreview);
          movieDetailsState = MovieDetailsState.ofContent(movieDetails);
        } catch (Exception e) {
          movieDetailsState = initialState
            .withError(e)
            .withLoading(false);
        }

        movieDetailsStateLiveData.postValue(movieDetailsState);
      }
    };
    executingThread.start();
  }

  public void onFavoriteClick() {
    final MovieDetailsState currentState = movieDetailsStateLiveData.getValue();
    if (currentState == null) return;
    executingThread = new Thread() {
      @Override
      public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        MovieDetails movie = currentState.content;
        int message;
        if (movie.isSaved) {
          movie = movieRepository.deleteMovie(movie);
          message = R.string.movie_removed;
        } else {
          movie = movieRepository.saveMovie(movie);
          message = R.string.movie_saved;
        }
        MovieDetailsState newState = MovieDetailsState
          .ofContent(movie)
          .withMessage(message);
        movieDetailsStateLiveData.postValue(newState);
      }
    };
    executingThread.start();
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    if (executingThread != null) {
      executingThread.interrupt();
    }
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