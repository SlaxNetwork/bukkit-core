package io.github.slaxnetwork.bukkitcore.minimessage.tags

import io.github.slaxnetwork.kyouko.models.profile.Profile
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object ProfileTags {
    /**
     * Replace <player_colored_name> with a colored version of the name provided.
     * @param name Player username.
     * @param profile Player profile.
     */
    fun coloredName(name: String, profile: Profile): TagResolver {
        return TagResolver.resolver(
            "player_colored_name",
            Tag.selfClosingInserting(Component
                .text(name).color(TextColor.fromHexString(profile.rank.color))
            ))
    }
}