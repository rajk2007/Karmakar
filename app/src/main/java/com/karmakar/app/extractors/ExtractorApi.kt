package com.karmakar.app.extractors

import com.karmakar.app.models.ExtractorLink
import com.karmakar.app.network.HttpClient

abstract class ExtractorApi {
    abstract val name: String
    abstract val mainUrl: String

    abstract suspend fun extract(url: String, callback: (ExtractorLink) -> Unit)

    fun getUrl(url: String, referer: String? = null): String =
        HttpClient.get(url, referer?.let { mapOf("Referer" to it) } ?: emptyMap())
}
