package com.nikita.pupularmoviesfirststage.posters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nikita.pupularmoviesfirststage.common.models.Poster;

import java.util.List;

public final class PostersAdapter extends RecyclerView.Adapter {

  private List<Poster> posters;

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  private static final class TopicHolder extends RecyclerView.ViewHolder {
    public final TextView title;

    public TopicHolder(View itemView) {
      super(itemView);
      title = (TextView) itemView;
    }
  }

  private static final class PosterHolder extends RecyclerView.ViewHolder {
    public final SimpleDraweeView image;

    public PosterHolder(View itemView) {
      super(itemView);
      image = (SimpleDraweeView) itemView;
    }
  }
}
