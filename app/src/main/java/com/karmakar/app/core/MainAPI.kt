package com.karmakar.app.core

import com.karmakar.app.models.ExtractorLink
import com.karmakar.app.models.LoadResponse
import com.karmakar.app.models.SearchResult

abstract class MainAPI {
    abstract val name: String
    abstract val mainUrl: String
    abstract val lang: String
    abstract val supportedTypes: List<String>

    abstract suspend fun search(query: String): List<SearchResult>
    abstract suspend fun load(url: String): LoadResponse?
    abstract suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (String) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean
}
