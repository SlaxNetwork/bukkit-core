package io.github.slaxnetwork.icon

import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.kyouko.models.icon.Icon
import kotlinx.serialization.json.*
import org.bukkit.Bukkit
import java.io.File
import java.util.Collections

class IconRegistryImpl(
    private val iconsFile: File
) : IconRegistry {
    private val _icons = mutableMapOf<String, Char>()

    override val icons: Map<String, Char>
        get() = Collections.unmodifiableMap(_icons)

    @Throws(NullPointerException::class)
    override suspend fun initialize() {
        if(!iconsFile.exists()) {
            throw NullPointerException("${iconsFile.canonicalPath} was not initialized on the server.")
        }

        val obj = Json.parseToJsonElement(iconsFile.readText())
            .jsonObject

        loadIcons(obj)
    }

    private fun loadIcons(iconsObj: JsonObject, prefix: String="") {
        for((k, v) in iconsObj.entries) {
            if(v is JsonPrimitive && v.isString) {
                _icons["${prefix}${k}"] = v.contentOrNull?.single()
                    ?: continue

                continue
            }

            if(v is JsonObject) {
                loadIcons(v, "${prefix}${k}_")
            }
        }
    }
}