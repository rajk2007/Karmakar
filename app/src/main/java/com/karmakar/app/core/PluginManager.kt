package com.karmakar.app.core

import android.content.Context
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File

object PluginManager {
    private val TAG = "PluginManager"
    private val loadedPlugins = mutableMapOf<String, Plugin>()

    fun loadPlugin(context: Context, file: File): Boolean {
        return try {
            val loader = DexClassLoader(
                file.absolutePath,
                context.codeCacheDir.absolutePath,
                null,
                context.classLoader
            )
            // Try known CloudStream plugin entry point class names
            val pluginClass = try {
                loader.loadClass("com.lagradost.cloudstream3.plugins.CloudstreamPlugin")
            } catch (e: ClassNotFoundException) {
                try {
                    loader.loadClass("Plugin")
                } catch (e2: ClassNotFoundException) {
                    null
                }
            }
            if (pluginClass == null) {
                Log.w(TAG, "No plugin class found in ${file.name}")
                return false
            }
            val plugin = pluginClass.getDeclaredConstructor().newInstance() as Plugin
            plugin.filename = file.name
            plugin.load()
            loadedPlugins[file.name] = plugin
            Log.d(TAG, "Loaded plugin: ${file.name}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load ${file.name}: ${e.message}")
            false
        }
    }

    fun loadAllPlugins(context: Context) {
        val pluginDir = File(context.filesDir, "plugins")
        if (!pluginDir.exists()) return
        pluginDir.listFiles()
            ?.filter { it.extension == "cs3" || it.extension == "apk" }
            ?.forEach { loadPlugin(context, it) }
    }

    fun getLoadedPlugins(): List<String> = loadedPlugins.keys.toList()

    fun unloadPlugin(filename: String) {
        loadedPlugins[filename]?.unload()
        loadedPlugins.remove(filename)
    }
}
