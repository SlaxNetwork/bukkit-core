package io.github.slaxnetwork.bukkitcore.utilities.config.model

import kotlinx.serialization.Serializable
import net.kyori.adventure.sound.Sound

@Serializable
data class ConfigSoundModel(
    val key: Key,
    val source: Sound.Source,
    val volume: Float,
    val pitch: Float
) {
    @Serializable
    data class Key(
        val namespace: String? = null,
        val value: String
    )

    fun toSound(): Sound {
        val key = net.kyori.adventure.key.Key.key(
            this.key.namespace ?: net.kyori.adventure.key.Key.MINECRAFT_NAMESPACE,
            this.key.value
        )
        return Sound.sound(key, source, volume, pitch)
    }
}
