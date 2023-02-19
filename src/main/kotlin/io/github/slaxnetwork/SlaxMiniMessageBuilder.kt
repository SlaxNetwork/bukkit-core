package io.github.slaxnetwork

import io.github.slaxnetwork.icon.IconRegistry
import io.github.slaxnetwork.minimessage.tags.IconTags
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class SlaxMiniMessageBuilder(
    iconRegistry: IconRegistry
) {
    private val iconTagHandler = IconTags(iconRegistry)

    /**
     * Create a new [MiniMessage] instance with all TagResolvers.
     */
    fun createInstance(): MiniMessage {
        val builder = MiniMessage.builder()

        builder.tags(TagResolver.resolver(
            TagResolver.standard(),
            TagResolver.resolver("icon", iconTagHandler::iconTag)
        ))

        return builder.build()
    }
}