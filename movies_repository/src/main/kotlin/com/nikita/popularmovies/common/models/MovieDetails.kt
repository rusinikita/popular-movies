package com.nikita.popularmovies.common.models

import android.arch.persistence.room.*

// Room cant work with kotlin constructors
// default parameters need to use constructor without params in room

@Entity(foreignKeys = arrayOf(ForeignKey(entity = MoviePreview::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movie_id"))),
  indices = arrayOf(Index(value = "movie_id")))
data class Review(@field:PrimaryKey var id: String = "",
                  @field:ColumnInfo(name = "movie_id") var movieId: String = "",
                  var author: String = "",
                  var content: String = "",
                  var url: String = "")

@Entity(foreignKeys = arrayOf(ForeignKey(entity = MoviePreview::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movie_id"))),
  indices = arrayOf(Index(value = "movie_id")))
data class Video(@field:PrimaryKey var id: String = "",
                 @field:ColumnInfo(name = "movie_id") var movieId: String = "",
                 var key: String = "",
                 var name: String = "",
                 var site: String = "",
                 var type: String = "") {
}