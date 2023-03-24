package io.github.slaxnetwork.bukkitcore.utilities.config.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigBorderModel(
    val radius: Double,
    val damage: Double,
    @SerialName("damage_buffer")
    val damageBuffer: Double
)