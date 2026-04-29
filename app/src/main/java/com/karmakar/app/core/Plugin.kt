package com.karmakar.app.core

abstract class Plugin {
    lateinit var filename: String
    abstract fun load()
    open fun unload() {}

    fun registerMainAPI(api: MainAPI) {
        APIHolder.register(api)
    }
}
