package io.github.slaxnetwork.listeners

import io.github.slaxnetwork.profile.ProfileRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(
    private val profileRegistry: ProfileRegistry
) : Listener {
    @EventHandler
    fun onPlayerQuit(ev: PlayerQuitEvent) {
        profileRegistry.remove(ev.player.uniqueId)
    }
}