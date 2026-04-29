package com.karmakar.app.models

data class SearchResult(
    val title: String,
    val posterUrl: String?,
    val url: String,
    val apiName: String = "",
    val type: String = "movie",
    val id: String = ""
)
