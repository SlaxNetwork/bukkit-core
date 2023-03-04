package io.github.slaxnetwork.icon

import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.kyouko.models.icon.Icon
import io.github.slaxnetwork.kyouko.services.v1.IconService
import java.util.Collections

class IconRegistryImpl(
    private val iconService: IconService
) : IconRegistry {
    private val _icons = mutableSetOf<Icon>()

    override val icons: Set<Icon>
        get() = Collections.unmodifiableSet(_icons)

    override val mappedIcons: Map<String, Char>
        get() = icons.associate { Pair(it.name, it.char) }

    override suspend fun initialize() {
        iconService.getAll()
            .getOrThrow()
            .forEach(_icons::add)
    }
}