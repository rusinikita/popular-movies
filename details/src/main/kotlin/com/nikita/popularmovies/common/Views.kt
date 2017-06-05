package com.nikita.popularmovies.common

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View

@Suppress("UNCHECKED_CAST")
fun <T : View> Activity.findView(@IdRes id: Int) = this.findViewById(id) as T

@Suppress("UNCHECKED_CAST")
fun <T : View> View.findView(@IdRes id: Int) = this.findViewById(id) as T

var View.isVisible: Boolean
  set(value) {
    this.visibility = if (value) View.VISIBLE else View.GONE
  }
  get() = this.visibility == View.VISIBLE