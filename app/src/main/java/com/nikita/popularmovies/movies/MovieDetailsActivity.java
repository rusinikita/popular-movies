package com.nikita.popularmovies.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nikita.popularmovies.R;
import com.nikita.popularmovies.common.models.MoviePreview;
import com.nikita.popularmovies.common.network.Request;

public final class MovieDetailsActivity extends AppCompatActivity {
  private static final String EXTRA_MOVIE = "movie";
  public static Intent getIntent(Context context, MoviePreview movie){
    Intent intent = new Intent(context, MovieDetailsActivity.class);
    intent.putExtra(EXTRA_MOVIE, movie);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.movie_details_activity);
    MoviePreview movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(movie.title);

    SimpleDraweeView backdrop = (SimpleDraweeView) findViewById(R.id.backdrop);
    backdrop.setImageURI(Request.posterImageUrl(movie.backdropPath));
    SimpleDraweeView poster = (SimpleDraweeView) findViewById(R.id.poster);
    poster.setImageURI(Request.posterImageUrl(movie.posterPath));
    TextView rating = (TextView) findViewById(R.id.rating);
    rating.setText(Double.toString(movie.voteAverage));
    TextView releaseDate = (TextView) findViewById(R.id.date);
    releaseDate.setText(movie.releaseDate);
    TextView overview = (TextView) findViewById(R.id.overview);
    overview.setText(movie.overview);
  }
}
