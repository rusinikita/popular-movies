package com.nikita.popularmovies.details;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nikita.popularmovies.Views;
import com.nikita.popularmovies.common.models.Video;
import com.nikita.popularmovies.common.network.Network;

import java.util.Collections;
import java.util.List;

public final class VideosAdapter extends PagerAdapter {
  private String backdrop = "";
  private List<Video> videos = Collections.emptyList();
  private VideoConsumer videoClickAction;

  public VideosAdapter(VideoConsumer videoClickAction) {
    this.videoClickAction = videoClickAction;
  }

  public interface VideoConsumer {
    void onVideoClick(Video video);
  }

  void changeData(String backdrop, List<Video> videos) {
    this.backdrop = backdrop;
    this.videos = videos;
    notifyDataSetChanged();
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.movie_details_video_item, container, false);

    SimpleDraweeView content = Views.findView(view, R.id.preview);
    final Video video = videos.get(position);
    content.setImageURI(video.site.toLowerCase().equals("youtube") ? ("http://img.youtube.com/vi/" + video.key + "/0.jpg") : Network.posterPathUrl(backdrop));
    container.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        videoClickAction.onVideoClick(video);
      }
    });

    container.addView(view);
    return view;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getCount() {
    return videos.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }
}
