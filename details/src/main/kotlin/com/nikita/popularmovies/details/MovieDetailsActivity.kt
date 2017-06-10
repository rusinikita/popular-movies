package com.nikita.popularmovies.details

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager
import com.nikita.popularmovies.Views
import com.nikita.popularmovies.common.models.MoviePreview
import com.nikita.popularmovies.common.models.Video
import com.nikita.popularmovies.common.network.Network

class MovieDetailsActivity : LifecycleActivity() {
  private lateinit var viewModel: MovieDetailsViewModel
  private lateinit var toolbar: Toolbar
  private lateinit var backdrop: SimpleDraweeView
  private lateinit var poster: SimpleDraweeView
  private lateinit var rating: TextView
  private lateinit var releaseDate: TextView
  private lateinit var overview: TextView
  private lateinit var saveButton: View
  private lateinit var progressBar: View
  private val videosAdapter = VideosAdapter({ openTrailer(it)})
  private val reviewsAdapter = ReviewsAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.movie_details_activity)
    initViews()
    subscribeViewModel()
  }

  private fun initViews() {
    toolbar = Views.findView(this, R.id.toolbar)
    backdrop = Views.findView(this, R.id.backdrop)
    poster = Views.findView(this, R.id.poster)
    rating = Views.findView(this, R.id.rating)
    releaseDate = Views.findView(this, R.id.date)
    overview = Views.findView(this, R.id.overview)
    saveButton = findViewById(R.id.fab)
    progressBar = findViewById(R.id.progress_bar)
    saveButton.setOnClickListener { viewModel.onFavoriteClick() }
    val videosPager: HorizontalInfiniteCycleViewPager = Views.findView(this, R.id.videos)
    videosPager.adapter = videosAdapter
    val reviewsPager: HorizontalInfiniteCycleViewPager = Views.findView(this, R.id.reviews)
    reviewsPager.adapter = reviewsAdapter
  }

  private fun subscribeViewModel() {
    val movie = intent.getParcelableExtra<MoviePreview>(EXTRA_MOVIE)
    val viewModel = ViewModelProviders.of(this, MovieDetailsViewModelFactory(this, movie)).get(MovieDetailsViewModel::class.java)

    this.viewModel = viewModel

    viewModel.movieDetailsLiveData.observe(this, Observer { data -> data?.let { render(it) } })
  }

  private fun render(model: MovieDetailsState) {
    val moviePreview = model.content.moviePreview

    toolbar.title = moviePreview.title
    backdrop.setImageURI(Network.posterPathUrl(moviePreview.backdropPath))
    poster.setImageURI(Network.posterPathUrl(moviePreview.posterPath))
    rating.text = moviePreview.voteAverage.toString()
    releaseDate.text = moviePreview.releaseDate
    overview.text = moviePreview.overview

    Views.setVisible(saveButton, !model.isLoading)
    Views.setVisible(progressBar, model.isLoading)

    videosAdapter.changeData(moviePreview.backdropPath, model.content.videos)
    reviewsAdapter.changeData(model.content.reviews)

    if (model.message > 0) {
      Snackbar.make(toolbar, model.message, Snackbar.LENGTH_SHORT).show()
    }
    model.error?.let { Snackbar.make(toolbar, model.error.message!!, Snackbar.LENGTH_SHORT).show() }
  }

  private fun openTrailer(video: Video) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=${video.key}")))
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
