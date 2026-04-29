package com.karmakar.app.models

data class ExtractorLink(
    val source: String,
    val name: String,
    val url: String,
    val referer: String = "",
    val quality: Int = -1,
    val isM3u8: Boolean = false,
    val headers: Map<String, String> = emptyMap()
)
