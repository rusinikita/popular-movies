package com.nikita.pupularmoviesfirststage;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public final class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
  }
}
