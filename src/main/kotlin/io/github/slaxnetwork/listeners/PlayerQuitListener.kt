package io.github.slaxnetwork.listeners

import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(
    private val profileRegistry: ProfileRegistry,
    private val scoreboardManager: ScoreboardManager
) : Listener {
    @EventHandler
    fun onPlayerQuit(ev: PlayerQuitEvent) {
        // flush player data
        profileRegistry.remove(ev.player.uniqueId)
        scoreboardManager.clearBoard(ev.player)
    }
}