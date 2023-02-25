package io.github.slaxnetwork

import io.github.slaxnetwork.icon.IconRegistry
import io.github.slaxnetwork.language.LanguageProvider
import io.github.slaxnetwork.minimessage.tags.IconTags
import io.github.slaxnetwork.minimessage.tags.LanguageTags
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class SlaxMiniMessageBuilder(
    iconRegistry: IconRegistry,
    languageProvider: LanguageProvider
) {
    private val iconTagHandler = IconTags(iconRegistry)
    private val languageTagHandler = LanguageTags(languageProvider)

    /**
     * Create a new [MiniMessage] instance with all TagResolvers.
     */
    fun createInstance(): MiniMessage {
        val builder = MiniMessage.builder()

        builder.tags(TagResolver.resolver(
            TagResolver.standard(),
            TagResolver.resolver("icon", iconTagHandler::iconTag),
            TagResolver.resolver("message", languageTagHandler::messageTag)
        ))

        return builder.build()
    }
}