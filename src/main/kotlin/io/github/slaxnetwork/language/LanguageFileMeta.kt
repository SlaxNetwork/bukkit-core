package io.github.slaxnetwork.language

import kotlinx.serialization.Serializable

@Serializable
data class LanguageFileMeta(
    val id: String,
    val name: String,
    val contributors: Set<String>,
    val type: String
)