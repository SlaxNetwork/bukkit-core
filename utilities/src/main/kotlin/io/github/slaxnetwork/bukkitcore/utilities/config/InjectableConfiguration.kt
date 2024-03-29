package io.github.slaxnetwork.bukkitcore.utilities.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.serializerOrNull
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass

private val configurationContainer = mutableMapOf<Any, Any>()
val CONFIGURATION_CONTAINER: Map<Any, Any>
    get() = Collections.unmodifiableMap(configurationContainer)

/**
 * Load the config.json file.
 * @param plugin Instance to accessing [JavaPlugin].
 * @param kClass [KClass] of the configuration.
 * @param replace Whether to replace existing resources or not.
 */
fun loadConfig(plugin: JavaPlugin, kClass: KClass<out Any>, replace: Boolean = true) {
    loadInjectableResources(plugin, mapOf("config.json" to kClass), replace)
}

/**
 * Load all configurations in resources.
 * @param plugin Instance to accessing [JavaPlugin].
 * @param resources Resources to load from the [JavaPlugin].
 * @param replace Whether to replace existing resources or not.
 */
@OptIn(ExperimentalSerializationApi::class)
fun loadInjectableResources(plugin: JavaPlugin, resources: Map<String, KClass<out Any>>, replace: Boolean = true) {
    for((resourcePath, kClass) in resources) {
        plugin.saveResource(resourcePath, replace)

        if(!resourcePath.endsWith(".json", true)) {
            continue
        }

        val serializer = serializerOrNull(kClass.javaObjectType)
            ?: throw NullPointerException("${kClass.simpleName} has no serializer attached to it.")

        val inputStream = File(plugin.dataFolder, resourcePath).inputStream()

        configurationContainer[kClass] = Json.decodeFromStream(serializer, inputStream)

        inputStream.close()
    }
}


/**
 * Inject a configuration by its type
 * @param Config Type of Configuration to inject.
 */
inline fun <reified Config> injectConfig() = ReadOnlyProperty<Any?, Config> { _, _ ->
    val conf = CONFIGURATION_CONTAINER[Config::class] as Config
        ?: throw NullPointerException("no configuration ${Config::class.simpleName} was found in the configuration container.")
    conf
}