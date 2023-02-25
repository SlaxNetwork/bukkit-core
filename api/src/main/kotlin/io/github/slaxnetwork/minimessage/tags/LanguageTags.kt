package io.github.slaxnetwork.minimessage.tags

import io.github.slaxnetwork.language.LanguageProvider
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import org.bukkit.Bukkit

class LanguageTags(
    private val languageProvider: LanguageProvider
) {
    /**
     * Converts an icon tag into its corresponding unicode character to be
     * handled by the resource pack.
     */
    fun messageTag(args: ArgumentQueue, ctx: Context): Tag {
        val arguments = args.popOr("The <message> tag requires an id to be passed.")
            .value()

        val sepIndex = arguments.indexOf('.')

        val languageId = arguments.substring(0, sepIndex)
        val messageId = arguments.substring(sepIndex + 1, arguments.length)

        return Tag.inserting(Component.text(
            languageProvider.messages[languageId]
                ?.get(messageId) ?: "FAIL"
        ))
    }

    companion object {
        // tmp
        private const val UNICODE_WARNING_SYMBOL = '당'
    }
}