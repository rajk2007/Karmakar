package com.karmakar.app.models

data class Episode(
    val name: String?,
    val data: String,
    val season: Int? = null,
    val episode: Int? = null,
    val posterUrl: String? = null
)
