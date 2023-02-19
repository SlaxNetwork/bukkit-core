package io.github.slaxnetwork

import io.github.slaxnetwork.icon.IconRegistry
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class SlaxMiniMessageBuilder(
    private val iconRegistry: IconRegistry
) {
    fun createInstance(): MiniMessage {
        val builder = MiniMessage.builder()

        builder.tags(TagResolver.resolver(
            TagResolver.standard(),
            TagResolver.resolver("icon", this::iconTag)
        ))

        return builder.build()
    }

    private fun iconTag(args: ArgumentQueue, ctx: Context): Tag {
        val iconId = args.popOr("The <icon> tag requires an id to be passed.")
            .value()

        return Tag.selfClosingInserting(Component
            .text(iconRegistry.mappedIcons[iconId] ?: 'f')
        )
    }
}