package com.karmakar.app.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.karmakar.app.network.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

data class RepoManifest(
    @SerializedName("plugins") val plugins: List<PluginManifest> = emptyList()
)

data class PluginManifest(
    @SerializedName("name") val name: String = "",
    @SerializedName("internalName") val internalName: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("version") val version: Int = 0,
    @SerializedName("iconUrl") val iconUrl: String? = null
)

object RepositoryManager {
    private val TAG = "RepositoryManager"
    private val gson = Gson()

    val DEFAULT_REPOS = listOf(
        "https://raw.githubusercontent.com/recloudstream/cloudstream-extensions/builds/repo.json",
        "https://raw.githubusercontent.com/phisher98/cloudstream-extensions-phisher/builds/repo.json",
        "https://raw.githubusercontent.com/Nivin-X/CNC-Repo/builds/repo.json",
        "https://raw.githubusercontent.com/doGior/doGiorHadEnough/builds/repo.json",
        "https://raw.githubusercontent.com/self-similarsupreme/csmegarepo/master/repo.json"
    )

    suspend fun installDefaultRepos(
        context: Context,
        onProgress: (String) -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        val pluginDir = File(context.filesDir, "plugins").also { it.mkdirs() }
        DEFAULT_REPOS.forEachIndexed { index, repoUrl ->
            try {
                onProgress("Fetching repo ${index + 1} of ${DEFAULT_REPOS.size}...")
                val json = HttpClient.get(repoUrl)
                val manifest = gson.fromJson(json, RepoManifest::class.java)
                manifest.plugins.forEach { plugin ->
                    try { downloadPlugin(plugin, pluginDir) }
                    catch (e: Exception) { Log.e(TAG, "Failed ${plugin.name}: ${e.message}") }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed repo $repoUrl: ${e.message}")
            }
        }
        onProgress("Setup complete!")
    }

    private fun downloadPlugin(plugin: PluginManifest, dir: File) {
        if (plugin.url.isBlank()) return
        val file = File(dir, "${plugin.internalName}.cs3")
        if (file.exists()) return
        val bytes = HttpClient.client.newCall(
            Request.Builder().url(plugin.url).build()
        ).execute().body?.bytes() ?: return
        file.writeBytes(bytes)
        Log.d(TAG, "Downloaded: ${plugin.name}")
    }

    fun isSetupDone(context: Context): Boolean =
        context.getSharedPreferences("karmakar", Context.MODE_PRIVATE)
            .getBoolean("setup_done", false)

    fun markSetupDone(context: Context) =
        context.getSharedPreferences("karmakar", Context.MODE_PRIVATE)
            .edit().putBoolean("setup_done", true).apply()
}
