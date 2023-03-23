package io.github.slaxnetwork.scoreboard

import io.github.slaxnetwork.BukkitCore
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import io.github.slaxnetwork.bukkitcore.scoreboard.SimpleScoreboard
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class ScoreboardManagerImpl : ScoreboardManager {
    private val inst get() = JavaPlugin.getPlugin(BukkitCore::class.java)

    private val boards = mutableMapOf<UUID, FastBoardImpl>()

    override fun setBoard(player: Player, board: SimpleScoreboard) {
        val fastBoard = FastBoardImpl(player)

        fastBoard.updateTitle(board.title)
        fastBoard.updateLines(board.lines)
    }
}