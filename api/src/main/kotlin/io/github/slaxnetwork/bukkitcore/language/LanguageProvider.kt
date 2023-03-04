package io.github.slaxnetwork.bukkitcore.language

interface LanguageProvider {
    val languages: Set<String>
    val messages: Map<String, Map<String, String>>

    fun register()
}