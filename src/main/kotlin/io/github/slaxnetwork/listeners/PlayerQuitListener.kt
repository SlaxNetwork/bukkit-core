package io.github.slaxnetwork.listeners

import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import io.github.slaxnetwork.koin.LibraryKoinComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class PlayerQuitListener : Listener, LibraryKoinComponent {
    private val profileRegistry: ProfileRegistry = get()
    private val scoreboardManager: ScoreboardManager = get()

    @EventHandler
    fun onPlayerQuit(ev: PlayerQuitEvent) {
        // flush player data
        profileRegistry.remove(ev.player.uniqueId)
        scoreboardManager.clearBoard(ev.player)
    }
}