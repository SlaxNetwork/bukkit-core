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
        val languageId = args.popOr("language id expected.").value()
        val messageId = args.popOr("message id expected.").value()

        val str = languageProvider.messages[languageId]
            ?.get(messageId) ?: "<MISSING STRING TABLE ENTRY $languageId:$messageId>"

        return Tag.preProcessParsed(str)
    }

    companion object {
        /**
         * Translate text for a player.
         * If the profile provided is null then it'll default to using en_us
         * @param id Message id.
         * @param profile Profile to translate for.
         */
        @JvmName("translateTextNullableProfile")
        fun translateText(id: String, profile: Profile?): TagResolver {
            return if(profile == null) {
                translateText("en_us", id)
            } else {
                translateText(id, profile)
            }
        }

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
            return translateText(id, "en_us")
        }

        /**
         * Get the tag
         */
        fun translateText(id: String, languageId: String): TagResolver {
            return Placeholder.parsed(
                "text",
                "<text:${languageId}:${id}>"
            )
        }
    }
}