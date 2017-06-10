package com.nikita.popularmovies.details;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikita.popularmovies.Views;
import com.nikita.popularmovies.common.models.Review;

import java.util.Collections;
import java.util.List;

public final class ReviewsAdapter extends PagerAdapter {
  private List<Review> reviews = Collections.emptyList();

  public void changeData(List<Review> data) {
    reviews = data;
    notifyDataSetChanged();
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.movie_details_review_item, container, false);

    TextView author = Views.findView(view, R.id.author);
    TextView content = Views.findView(view, R.id.content);
    Review review = reviews.get(position);
    author.setText(review.author);
    content.setText(review.content);

    container.addView(view);
    return view;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getCount() {
    return reviews.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }
}