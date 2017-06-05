package com.nikita.popularmovies.details

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikita.popularmovies.common.findView
import com.nikita.popularmovies.common.models.Review

class ReviewsAdapter(private var reviews: List<Review> = emptyList()) : PagerAdapter() {
  fun changeData(data: List<Review>) {
    reviews = data
    notifyDataSetChanged()
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val view = LayoutInflater.from(container.context).inflate(R.layout.movie_details_review_item, container, false)

    val author: TextView = view.findView(R.id.author)
    val content: TextView = view.findView(R.id.content)
    val review = reviews[position]
    author.text = review.author
    content.text = review.content

    container.addView(view)
    return view
  }

  override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
    container.removeView(obj as View)
  }

  override fun isViewFromObject(view: View, obj: Any) = view == obj

  override fun getCount() = reviews.size
}