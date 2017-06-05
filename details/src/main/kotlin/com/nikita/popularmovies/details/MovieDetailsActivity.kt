package com.nikita.popularmovies.details

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager
import com.nikita.popularmovies.common.findView
import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.network.posterPathUrl

class MovieDetailsActivity : LifecycleActivity() {
  private lateinit var toolbar: Toolbar
  private lateinit var backdrop: SimpleDraweeView
  private lateinit var poster: SimpleDraweeView
  private lateinit var rating: TextView
  private lateinit var releaseDate: TextView
  private lateinit var overview: TextView
  private val videosAdapter = VideosAdapter(videoClickAction = { TODO()})

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.movie_details_activity)
    initViews()
    subscribeViewModel(savedInstanceState)
  }

  private fun initViews() {
    toolbar = findView(R.id.toolbar)
    backdrop = findView(R.id.backdrop)
    poster = findView(R.id.poster)
    rating = findView(R.id.rating)
    releaseDate = findView(R.id.date)
    overview = findView(R.id.overview)
    val videosPager: HorizontalInfiniteCycleViewPager = findView(R.id.videos)
    videosPager.adapter = videosAdapter
  }

  private fun subscribeViewModel(savedInstanceState: Bundle?) {
    val movie = intent.getParcelableExtra<MoviePreview>(EXTRA_MOVIE)
    val viewModel = ViewModelProviders.of(this, MovieDetailsViewModelFactory(this, movie)).get(MovieDetailsViewModel::class.java)
    viewModel.movieDetailsLiveData.observe(this, Observer { data -> data?.let { render(it) } })
  }

  private fun render(model: MovieDetailsScreen) {
    val moviePreview = model.content.moviePreview

    toolbar.title = moviePreview.title
    backdrop.setImageURI(moviePreview.backdropPath.posterPathUrl)
    poster.setImageURI(moviePreview.posterPath.posterPathUrl)
    rating.text = moviePreview.voteAverage.toString()
    releaseDate.text = moviePreview.releaseDate
    overview.text = moviePreview.overview

    videosAdapter.changeData(moviePreview.backdropPath, model.content.videos)
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
