package com.nikita.popularmovies.common.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikita.popularmovies.R;

public final class ErrorView extends LinearLayout {
  private final TextView errorTitle;
  private final View retryButton;

  public ErrorView(Context context) {
    this(context, null);
  }

  public ErrorView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOrientation(LinearLayout.VERTICAL);
    setGravity(Gravity.CENTER);
    LayoutInflater.from(context).inflate(R.layout.error_view_content, this, true);
    errorTitle = (TextView) findViewById(R.id.error_title);
    retryButton = findViewById(R.id.retry_button);
  }

  public void showError(String errorTitle, final Runnable retryAction) {
    this.errorTitle.setText(errorTitle);
    this.retryButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        retryAction.run();
      }
    });
    setVisibility(VISIBLE);
  }

  public void hideError() {
    setVisibility(GONE);
    retryButton.setOnClickListener(null);
    errorTitle.setText(null);
  }
}
