package io.github.slaxnetwork.bukkitcore.icon

interface IconRegistry {
    val icons: Map<String, Char>

    suspend fun initialize()
}