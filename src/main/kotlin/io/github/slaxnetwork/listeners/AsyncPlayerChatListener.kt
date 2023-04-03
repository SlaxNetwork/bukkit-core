package io.github.slaxnetwork.listeners

import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.bukkitcore.minimessage.tags.ProfileTags
import io.github.slaxnetwork.bukkitcore.minimessage.tags.RankTags
import io.github.slaxnetwork.mm
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.koin.LibraryKoinComponent
import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.koin.core.component.get

class AsyncPlayerChatListener : Listener, LibraryKoinComponent {
    private val profileRegistry: ProfileRegistry = get()

    @EventHandler
    fun onPlayerChat(ev: AsyncChatEvent) {
        val profile = profileRegistry.profiles[ev.player.uniqueId]
            ?: return

        ev.renderer(SlaxChatRenderer(profile))
    }

    private class SlaxChatRenderer(
        private val profile: Profile
    ) : ChatRenderer {
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
