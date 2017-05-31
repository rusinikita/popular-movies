package com.nikita.popularmovies.common.models

data class Review(val author: String, val content: String, val url: String)

data class Video(val id: String, val key: String, val name: String, val site: String, val type: String)