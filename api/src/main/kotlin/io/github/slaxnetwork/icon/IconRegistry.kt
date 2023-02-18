package io.github.slaxnetwork.icon

import io.github.slaxnetwork.kyouko.models.icon.Icon

interface IconRegistry {
    val icons: Set<Icon>
    val mappedIcons: Map<String, Char>

    suspend fun initialize()

//    fun addAll(icons: Set<Icon>): Set<Icon>
}