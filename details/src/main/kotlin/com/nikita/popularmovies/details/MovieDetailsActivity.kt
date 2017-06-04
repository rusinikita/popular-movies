package com.nikita.popularmovies.details

import android.arch.lifecycle.LifecycleActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.network.posterPathUrl

class MovieDetailsActivity : LifecycleActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.movie_details_activity)
    val movie = intent.getParcelableExtra<MoviePreview>(EXTRA_MOVIE)

    val toolbar = findViewById(R.id.toolbar) as Toolbar
    toolbar.title = movie.title

    val backdrop = findViewById(R.id.backdrop) as SimpleDraweeView
    backdrop.setImageURI(movie.backdropPath.posterPathUrl)
    val poster = findViewById(R.id.poster) as SimpleDraweeView
    poster.setImageURI(movie.posterPath.posterPathUrl)
    val rating = findViewById(R.id.rating) as TextView
    rating.text = movie.voteAverage.toString()
    val releaseDate = findViewById(R.id.date) as TextView
    releaseDate.text = movie.releaseDate
    val overview = findViewById(R.id.overview) as TextView
    overview.text = movie.overview
  }

  companion object {
    private val EXTRA_MOVIE = "movie"
    @JvmStatic fun getIntent(context: Context, movie: MoviePreview): Intent {
      val intent = Intent(context, MovieDetailsActivity::class.java)
      intent.putExtra(EXTRA_MOVIE, movie)
      return intent
    }
  }
}
