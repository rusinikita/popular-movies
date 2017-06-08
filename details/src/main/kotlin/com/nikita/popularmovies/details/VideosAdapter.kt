package com.nikita.popularmovies.details

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.nikita.popularmovies.common.findView
import com.nikita.popularmovies.common.models.Video
import com.nikita.popularmovies.common.network.Network

class VideosAdapter(private var backdrop: String = "",
                    private var videos: List<Video> = emptyList(),
                    private val videoClickAction: (Video) -> Unit) : PagerAdapter() {

  fun changeData(backdrop: String, videos: List<Video>) {
    this.backdrop = backdrop
    this.videos = videos
    notifyDataSetChanged()
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val view = LayoutInflater.from(container.context).inflate(R.layout.movie_details_video_item, container, false)

    val preview: SimpleDraweeView = view.findView(R.id.preview)
    val video = videos[position]
    preview.setImageURI(if (video.site.toLowerCase() == "youtube") "http://img.youtube.com/vi/${video.key}/0.jpg" else Network.posterPathUrl(backdrop))
    preview.setOnClickListener({ videoClickAction(video) })

    container.addView(view)
    return view
  }

  override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
    container.removeView(obj as View)
  }

  override fun isViewFromObject(view: View, obj: Any) = view == obj

  override fun getCount() = videos.size
}