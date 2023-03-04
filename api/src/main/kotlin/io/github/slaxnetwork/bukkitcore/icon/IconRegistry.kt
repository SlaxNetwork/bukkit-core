package io.github.slaxnetwork.bukkitcore.icon

import io.github.slaxnetwork.kyouko.models.icon.Icon

interface IconRegistry {
    val icons: Set<Icon>
    val mappedIcons: Map<String, Char>

    suspend fun initialize()
}