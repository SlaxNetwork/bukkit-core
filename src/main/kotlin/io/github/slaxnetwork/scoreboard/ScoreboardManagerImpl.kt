package io.github.slaxnetwork.scoreboard

import io.github.slaxnetwork.BukkitCore
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import io.github.slaxnetwork.bukkitcore.scoreboard.SimpleScoreboard
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import java.util.UUID

class ScoreboardManagerImpl(
    private val scheduler: BukkitScheduler
) : ScoreboardManager {
    private val inst get() = JavaPlugin.getPlugin(BukkitCore::class.java)

    private val boards = mutableMapOf<UUID, FastBoardImpl>()

    override fun setBoard(player: Player, board: SimpleScoreboard) {
        getBoard(player)?.let {
            it.delete()
            boards.remove(player.uniqueId)
        }

        val fastBoard = FastBoardImpl(player, board)

        fastBoard.updateTitle(board.title)
        fastBoard.updateLines(board.lines)
    }

    override fun updateLine(player: Player, line: Int) {
        val board = getBoard(player)
            ?: return
        board.updateLineIndex(line)
    }

    override fun updateLine(player: Player, id: String) {
        val board = getBoard(player)
            ?: return
        board.updateLineById(id)
    }

    private fun getBoard(player: Player): FastBoardImpl? {
        return boards[player.uniqueId]
    }
}