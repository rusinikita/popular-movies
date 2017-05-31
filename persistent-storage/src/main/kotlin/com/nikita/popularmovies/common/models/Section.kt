package com.nikita.popularmovies.common.models

import android.support.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(MOVIE, TV_SHOW, PERSON)
annotation class Section

const val MOVIE = "movie"
const val TV_SHOW = "tv"
const val PERSON = "person"