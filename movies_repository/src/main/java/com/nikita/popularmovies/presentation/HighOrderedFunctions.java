package com.nikita.popularmovies.presentation;


import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public final class HighOrderedFunctions {

  public static void foo(Context context) {
    View view = new View(context);

    setVisible(view, true);
    boolean visible = isVisible(view);







    showThemedDialog(context, new Func<AlertDialog.Builder>() {
      @Override
      public AlertDialog.Builder accept(AlertDialog.Builder builder) {
        return builder.setMessage("blabla");
      }
    });
  }



  public static void setVisible(View view, boolean isVisible) {
    view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
  }

  public static boolean isVisible(View view) {
    return view.getVisibility() == View.VISIBLE;
  }






  public static interface Func<T> {
    public T accept(T t);
  }

  public static void showThemedDialog(Context context, Func<AlertDialog.Builder> setupFunction) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context)
      .setIcon(android.R.drawable.ic_dialog_alert);

    setupFunction
      .accept(builder)
      .show();
  }
}
