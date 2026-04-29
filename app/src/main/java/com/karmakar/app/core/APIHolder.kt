package com.karmakar.app.core

import com.karmakar.app.models.SearchResult

object APIHolder {
    val apis = mutableListOf<MainAPI>()

    fun register(api: MainAPI) {
        if (apis.none { it.name == api.name }) apis.add(api)
    }

    fun unregister(name: String) {
        apis.removeAll { it.name == name }
    }

    fun getApi(name: String): MainAPI? = apis.find { it.name == name }

    suspend fun search(query: String): List<SearchResult> {
        return apis.flatMap {
            try { it.search(query) } catch (e: Exception) { emptyList() }
        }
    }
}
