package io.github.slaxnetwork.bukkitcore.minimessage.tags

import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.kyouko.models.profile.Profile
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class LanguageTags(
    private val languageProvider: LanguageProvider
) {
    /**
     * Converts an icon tag into its corresponding unicode character to be
     * handled by the resource pack.
     */
    fun textTag(args: ArgumentQueue, ctx: Context): Tag {
        val arguments = args.popOr("The <text> tag requires an id to be passed.")
            .value()

        val sepIndex = arguments.indexOf('.')

        val languageId = arguments.substring(0, sepIndex)
        val messageId = arguments.substring(sepIndex + 1, arguments.length)

        val str = languageProvider.messages[languageId]
            ?.get(messageId) ?: "<MISSING STRING TABLE ENTRY $languageId.$messageId>"

        return Tag.preProcessParsed(str)
    }

    companion object {
        /**
         * Translate text for a player.
         * @param id Message id.
         * @param profile Profile to translate for.
         */
        fun translateText(id: String, profile: Profile): TagResolver {
            return translateText(id, profile.settings.language)
        }

        /**
         * Get text from the en_us language file.
         * @param id Message id.
         */
        fun getText(id: String): TagResolver {
            return translateText("en_us", id)
        }

        /**
         * Get the ta
         */
        fun translateText(id: String, languageId: String): TagResolver {
            return Placeholder.parsed(
                "text",
                "<text:${languageId}.${id}>"
            )
        }
    }
}