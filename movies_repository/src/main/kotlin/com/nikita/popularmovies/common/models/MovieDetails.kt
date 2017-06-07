package com.nikita.popularmovies.common.models

import android.arch.persistence.room.*

// Room cant work with kotlin constructors
// default parameters need to use constructor without params in room

@Entity(foreignKeys = arrayOf(ForeignKey(entity = MoviePreview::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movie_id"))),
  indices = arrayOf(Index(value = "movie_id")))
data class Review(@field:PrimaryKey val id: String,
                  @field:ColumnInfo(name = "movie_id") val movieId: String,
                  val author: String,
                  val content: String,
                  val url: String)

@Entity(foreignKeys = arrayOf(ForeignKey(entity = MoviePreview::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movie_id"))),
  indices = arrayOf(Index(value = "movie_id")))
data class Video(@field:PrimaryKey val id: String,
                 @field:ColumnInfo(name = "movie_id") val movieId: String,
                 val key: String,
                 val name: String,
                 val site: String,
                 val type: String)