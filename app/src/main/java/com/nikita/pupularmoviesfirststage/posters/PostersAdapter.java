package com.nikita.pupularmoviesfirststage.posters;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nikita.pupularmoviesfirststage.R;
import com.nikita.pupularmoviesfirststage.common.Constants;
import com.nikita.pupularmoviesfirststage.common.models.Poster;
import com.nikita.pupularmoviesfirststage.common.network.Request;

import java.util.Collections;
import java.util.List;

public final class PostersAdapter extends RecyclerView.Adapter {

  @Request.MovieTopic
  private String movieTopic = "";
  private List<Poster> posters = Collections.emptyList();
  private int[] placeholderColors;

  public void setTopic(@Request.MovieTopic String movieTopic) {
    this.movieTopic = movieTopic;
    notifyItemChanged(0);
  }

  public void setPosters(List<Poster> posters) {
    this.posters = posters;
    notifyDataSetChanged();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    initPlaceholdersIfNeed(parent.getResources());

    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    LayoutParams lp = (LayoutParams) view.getLayoutParams();
    float itemsWidth = parent.getWidth() / 2;
    int posterHeight = Math.round(itemsWidth * Constants.POSTER_ASPECT_RATIO);
    RecyclerView.ViewHolder holder;
    switch (viewType) {
      case R.layout.posters_item_topic:
        holder = new TopicHolder(view);
        lp.height = posterHeight / 3;
        break;
      default:
        holder = new PosterHolder(view);
        lp.height = posterHeight;
    }
    return holder;
  }

  private void initPlaceholdersIfNeed(Resources resources) {
    if (placeholderColors == null) {
      placeholderColors = resources.getIntArray(R.array.loading_placeholders_dark);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof TopicHolder) {
      ((TopicHolder) holder).title.setText(movieTopic);
    } else if (holder instanceof PosterHolder) {
      PosterHolder posterHolder = (PosterHolder) holder;
      int placeholderColor = placeholderColors[position % placeholderColors.length];
      GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(posterHolder.image.getResources());
      GenericDraweeHierarchy hierarchy = builder
        .setFadeDuration(200)
        .setPlaceholderImage(new ColorDrawable(placeholderColor))
        .build();
      posterHolder.image.setHierarchy(hierarchy);
      posterHolder.image.setImageURI(Request.posterImageUrl(posters.get(position - 1).posterPath()));
    }
  }

  @Override
  public int getItemCount() {
    return posters.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    int type;
    switch (position) {
      case 0:
        type = R.layout.posters_item_topic;
        break;
      default:
        type = R.layout.posters_item;
    }
    return type;
  }

  private static final class TopicHolder extends RecyclerView.ViewHolder {
    final TextView title;

    TopicHolder(View itemView) {
      super(itemView);
      title = (TextView) itemView;
    }
  }

  private static final class PosterHolder extends RecyclerView.ViewHolder {
    final SimpleDraweeView image;

    PosterHolder(View itemView) {
      super(itemView);
      image = (SimpleDraweeView) itemView;
    }
  }
}
