package com.nikita.popularmovies.details;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.nikita.popularmovies.Views;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.models.Video;
import com.nikita.popularmovies.common.network.Network;

public class MovieDetailsActivity extends LifecycleActivity implements VideosAdapter.VideoConsumer {
  public static final String EXTRA_MOVIE = "movie";
  private MovieDetailsViewModel viewModel;
  private Toolbar toolbar;
  private SimpleDraweeView backdrop;
  private SimpleDraweeView poster;
  private TextView rating;
  private TextView releaseDate;
  private TextView overview;
  private View saveButton;
  private View progressBar;
  private View errorTitle;
  private TextView errorMessage;
  private final VideosAdapter videosAdapter = new VideosAdapter(this);
  private final ReviewsAdapter reviewsAdapter = new ReviewsAdapter();

  public static Intent getIntent(Context context, MoviePreview movie) {
    Intent intent = new Intent(context, MovieDetailsActivity.class);
    intent.putExtra(EXTRA_MOVIE, movie);
    return intent;
  }

  @Override
  public void onVideoClick(Video video) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.key));
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivity(intent);
    }
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.movie_details_activity);
    initViews();
    subscribeViewModel();
  }

  private void initViews() {
    toolbar = Views.findView(this, R.id.toolbar);
    backdrop = Views.findView(this, R.id.backdrop);
    poster = Views.findView(this, R.id.poster);
    rating = Views.findView(this, R.id.rating);
    releaseDate = Views.findView(this, R.id.date);
    overview = Views.findView(this, R.id.overview);
    errorMessage = Views.findView(this, R.id.error_view);
    errorTitle = findViewById(R.id.error_title);
    saveButton = findViewById(R.id.fab);
    progressBar = findViewById(R.id.progress_bar);
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        viewModel.onFavoriteClick();
      }
    });
    HorizontalInfiniteCycleViewPager videosPager = Views.findView(this, R.id.videos);
    videosPager.setAdapter(videosAdapter);
    HorizontalInfiniteCycleViewPager reviewsPager = Views.findView(this, R.id.reviews);
    reviewsPager.setAdapter(reviewsAdapter);
  }

  private void subscribeViewModel() {
    MoviePreview movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
    MovieDetailsViewModel viewModel = ViewModelProviders.of(this, new MovieDetailsViewModel.Factory(this, movie)).get(MovieDetailsViewModel.class);

    this.viewModel = viewModel;

    viewModel.movieDetailsStateLiveData.observe(this, new Observer<MovieDetailsState>() {
      @Override
      public void onChanged(@Nullable MovieDetailsState state) {
        if (state != null) {
          render(state);
        }
      }
    });
  }

  private void render(MovieDetailsState state) {
    MoviePreview moviePreview = state.content.moviePreview;

    toolbar.setTitle(moviePreview.title);
    backdrop.setImageURI(Network.posterPathUrl(moviePreview.backdropPath));
    poster.setImageURI(Network.posterPathUrl(moviePreview.posterPath));
    rating.setText(String.valueOf(moviePreview.voteAverage));
    releaseDate.setText(moviePreview.releaseDate);
    overview.setText(moviePreview.overview);

    boolean hasError = state.error != null;
    Views.setVisible(saveButton, !state.isLoading && !hasError);
    Views.setVisible(progressBar, state.isLoading);
    Views.setVisible(errorMessage, hasError);
    Views.setVisible(errorTitle, hasError);
    if (hasError) {
      errorMessage.setText(state.error.getMessage());
    }

    videosAdapter.changeData(moviePreview.backdropPath, state.content.videos);
    reviewsAdapter.changeData(state.content.reviews);

    if (state.message > 0) {
      Snackbar.make(toolbar, state.message, Snackbar.LENGTH_SHORT).show();
    }
  }
}
