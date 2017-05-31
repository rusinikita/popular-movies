package com.nikita.popularmovies.common.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = MovieDetails::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movie_id"))))
data class Review(@field:PrimaryKey val id: String,
                  @field:ColumnInfo(name = "movie_id") val movieId: String,
                  val author: String,
                  val content: String,
                  val url: String)

@Entity(foreignKeys = arrayOf(ForeignKey(entity = MovieDetails::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movie_id"))))
data class Video(@field:PrimaryKey val id: String,
                 @field:ColumnInfo(name = "movie_id") val movieId: String,
                 val key: String,
                 val name: String,
                 val site: String,
                 val type: String)