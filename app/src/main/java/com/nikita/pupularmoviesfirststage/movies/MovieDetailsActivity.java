package com.nikita.pupularmoviesfirststage.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nikita.pupularmoviesfirststage.R;

public final class MovieDetailsActivity extends AppCompatActivity {
  public static Intent getIntent(Context context, Movie movie){
    Intent intent = new Intent(context, MovieDetailsActivity.class);

    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.movie_details_activity);
  }
}
