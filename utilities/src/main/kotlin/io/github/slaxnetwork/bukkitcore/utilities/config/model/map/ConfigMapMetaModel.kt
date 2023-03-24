package io.github.slaxnetwork.bukkitcore.utilities.config.model.map

import kotlinx.serialization.Serializable

@Serializable
data class ConfigMapMetaModel(
    val name: String,
    val contributors: List<String>
)