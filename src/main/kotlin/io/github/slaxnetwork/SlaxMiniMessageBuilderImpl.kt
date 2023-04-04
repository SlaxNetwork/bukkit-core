package io.github.slaxnetwork

import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.bukkitcore.minimessage.SlaxMiniMessageBuilder
import io.github.slaxnetwork.bukkitcore.minimessage.tags.IconTags
import io.github.slaxnetwork.bukkitcore.minimessage.tags.LanguageTags
import io.github.slaxnetwork.koin.LibraryKoinComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.koin.core.component.get

class SlaxMiniMessageBuilderImpl : SlaxMiniMessageBuilder, LibraryKoinComponent {
    private val iconRegistry: IconRegistry = get()
    private val languageProvider: LanguageProvider = get()

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