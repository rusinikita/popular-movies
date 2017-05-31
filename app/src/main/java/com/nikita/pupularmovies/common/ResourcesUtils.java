package com.nikita.pupularmovies.common;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.nikita.pupularmovies.R;
import com.nikita.pupularmovies.common.network.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class ResourcesUtils {
  @StringRes
  public static int topicTitleRes(@Request.MovieTopic String topic) {
    switch (topic) {
      case Request.POPULAR:
        return R.string.poster_topic_popular;
      case Request.TOP_RATED:
        return R.string.poster_topic_top_rated;
      case Request.UPCOMING:
        return R.string.poster_topic_upcoming;
      case Request.NOW_PLAYING:
        return R.string.poster_topic_now_playing;
      default:
        throw new IllegalArgumentException();
    }
  }

  @DrawableRes
  public static int topicIconRes(@Request.MovieTopic String topic) {
    switch (topic) {
      case Request.POPULAR:
        return R.drawable.ic_heart;
      case Request.TOP_RATED:
        return R.drawable.ic_sort_ascending;
      case Request.UPCOMING:
        return R.drawable.ic_clock_fast;
      case Request.NOW_PLAYING:
        return R.drawable.ic_popcorn_box;
      default:
        throw new IllegalArgumentException();
    }
  }

  /**WARNING Do not pass buttonCount close to color count. More than 5**/
  public static List<Integer> getBoomButtonColors(Context context, int buttonCount) {
    List<Integer> colors = new LinkedList<>();
    int[] allColorsArray = context.getResources().getIntArray(R.array.boom_button_colors);
    Random random = new Random();
    for (int i = 0; i < buttonCount; i++) {
      int color;
      do {
        color = allColorsArray[random.nextInt(allColorsArray.length)];
      } while (colors.contains(color));
      colors.add(color);
    }
    return colors;
  }
}
