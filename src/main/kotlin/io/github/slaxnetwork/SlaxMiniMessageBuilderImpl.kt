package io.github.slaxnetwork

import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.bukkitcore.minimessage.SlaxMiniMessageBuilder
import io.github.slaxnetwork.bukkitcore.minimessage.tags.IconTags
import io.github.slaxnetwork.bukkitcore.minimessage.tags.LanguageTags
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class SlaxMiniMessageBuilderImpl(
    iconRegistry: IconRegistry,
    languageProvider: LanguageProvider
): SlaxMiniMessageBuilder {
    private val iconTagHandler = IconTags(iconRegistry)
    private val languageTagHandler = LanguageTags(languageProvider)

    /**
     * Create a new [MiniMessage] instance with all TagResolvers.
     */
    fun createInstance(): MiniMessage {
        return getBaseMiniMessageBuilder()
            .build()
    }

    override fun getBaseMiniMessageBuilder(): MiniMessage.Builder {
        val builder = MiniMessage.builder()

        builder.tags(getResolvers())

        return builder
    }

    override fun getResolvers(): TagResolver {
        return TagResolver.resolver(
            TagResolver.standard(),
            TagResolver.resolver("icon", iconTagHandler::iconTag),
            TagResolver.resolver("text", languageTagHandler::textTag)
        )
    }
}