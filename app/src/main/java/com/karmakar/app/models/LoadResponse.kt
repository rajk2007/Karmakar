package com.karmakar.app.models

data class LoadResponse(
    val title: String,
    val url: String,
    val apiName: String,
    val type: String,
    val posterUrl: String?,
    val plot: String? = null,
    val year: Int? = null,
    val rating: Int? = null,
    val episodes: List<Episode>? = null
)
