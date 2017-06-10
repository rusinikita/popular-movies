package com.nikita.popularmovies;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by Nikita on 10.06.2017.
 */

public final class Views {
  public static <T extends View> T findView(Activity activity, @IdRes int id) {
    return (T) activity.findViewById(id);
  }
  public static <T extends View> T findView(View view, @IdRes int id) {
    return (T) view.findViewById(id);
  }

  public static void setVisible(View view, boolean visible) {
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
  }
}
