package io.github.slaxnetwork.bukkitcore.minimessage

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

interface SlaxMiniMessageBuilder {
    fun getBaseMiniMessageBuilder(): MiniMessage.Builder

    fun getResolvers(): TagResolver
}