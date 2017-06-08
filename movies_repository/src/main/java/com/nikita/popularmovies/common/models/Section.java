package com.nikita.popularmovies.common.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.provider.Telephony.TextBasedSmsColumns.PERSON;
import static com.nikita.popularmovies.common.models.Section.MOVIE;
import static com.nikita.popularmovies.common.models.Section.TV_SHOW;


@Retention(RetentionPolicy.SOURCE)
@StringDef(value = { MOVIE, TV_SHOW, PERSON })
public @interface Section {
  String MOVIE = "movie";
  String TV_SHOW = "tv";
  String PERSON = "person";
}
