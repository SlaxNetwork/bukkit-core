package io.github.slaxnetwork.listeners

import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.minimessage.tags.ProfileTags
import io.github.slaxnetwork.minimessage.tags.RankTags
import io.github.slaxnetwork.mm
import io.github.slaxnetwork.profile.ProfileRegistry
import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class AsyncPlayerChatListener(
    private val profileRegistry: ProfileRegistry
) : Listener {
    @EventHandler
    fun onPlayerChat(ev: AsyncChatEvent) {
        val profile = profileRegistry.mappedProfiles[ev.player.uniqueId]
            ?: return

        ev.renderer(SlaxChatRenderer(profile))
    }

    private class SlaxChatRenderer(
        private val profile: Profile
    ): ChatRenderer {
        override fun render(
            source: Player,
            sourceDisplayName: Component,
            message: Component,
            viewer: Audience
        ): Component {
            return mm.deserialize(
                "<rank_icon> <player_colored_name><reset>: <message>",
                RankTags.icon(profile.rank),
                ProfileTags.coloredName(source.name, profile),
                Placeholder.component("message", message)
            )
        }
    }
}
