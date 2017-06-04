package com.nikita.popularmovies.common

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View

@Suppress("UNCHECKED_CAST")
fun <T : View> Activity.findView(@IdRes id: Int) = this.findViewById(id) as T