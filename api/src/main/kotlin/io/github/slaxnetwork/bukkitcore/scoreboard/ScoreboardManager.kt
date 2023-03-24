package io.github.slaxnetwork.bukkitcore.scoreboard

import io.github.slaxnetwork.bukkitcore.scoreboard.types.SimpleScoreboard
import org.bukkit.entity.Player
import org.bukkit.plugin.ServicesManager

interface ScoreboardManager {
    fun setBoard(player: Player, board: SimpleScoreboard): FastBoard

    fun clearBoard(player: Player)

    fun getFastBoard(player: Player): FastBoard?

    fun updateAllBoardLine(boardId: String, lineId: String)

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