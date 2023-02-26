package io.github.slaxnetwork.minimessage.tags

import io.github.slaxnetwork.kyouko.models.profile.Profile
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Bukkit

object ProfileTags {
    fun coloredName(name: String, profile: Profile): TagResolver {
        return TagResolver.resolver(
            "player_colored_name",
            Tag.selfClosingInserting(Component
                .text(name).color(TextColor.fromHexString(profile.rank.color))
            ))
    }

    fun translateText(id: String, profile: Profile): TagResolver {
        return translateText(id, profile.settings.language)
    }

    fun translateText(id: String, languageId: String): TagResolver {
        return Placeholder.parsed(
            "text",
            "<text:${languageId}.${id}>"
        )
    }
}