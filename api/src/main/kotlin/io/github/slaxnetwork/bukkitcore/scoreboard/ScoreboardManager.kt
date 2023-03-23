package io.github.slaxnetwork.bukkitcore.scoreboard

import org.bukkit.entity.Player
import org.bukkit.plugin.ServicesManager

interface ScoreboardManager {
    fun setBoard(player: Player, board: SimpleScoreboard)

    companion object {
        fun get(sm: ServicesManager): ScoreboardManager? {
            if(sm.isProvidedFor(ScoreboardManager::class.java)) {
                return sm.getRegistration(ScoreboardManager::class.java)
                    ?.provider
            }

            return null
        }
    }
}