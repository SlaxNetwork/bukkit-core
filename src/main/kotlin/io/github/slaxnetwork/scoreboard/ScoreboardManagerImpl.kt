package io.github.slaxnetwork.scoreboard

import io.github.slaxnetwork.bukkitcore.scoreboard.FastBoard
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import io.github.slaxnetwork.bukkitcore.scoreboard.types.SimpleScoreboard
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitScheduler
import java.util.UUID

class ScoreboardManagerImpl(
    private val scheduler: BukkitScheduler
) : ScoreboardManager {
    private val boards = mutableMapOf<UUID, FastBoardImpl>()
    override fun setBoard(player: Player, board: SimpleScoreboard): FastBoard {
        clearBoard(player)

        // initialize our fast board.
        val fastBoard = FastBoardImpl(player, board)
        fastBoard.updateTitle(board.title)
        fastBoard.updateLines(board.lines)

        boards[player.uniqueId] = fastBoard

        return fastBoard
    }

    override fun clearBoard(player: Player) {
        val board = getFastBoard(player)
            ?: return

        if(player.isOnline && !board.isDeleted) {
            board.delete()
        }
        boards.remove(player.uniqueId)
    }

    override fun getFastBoard(player: Player): FastBoard? {
        return boards[player.uniqueId]
    }

    override fun switchBoardForAll(boardId: String) {
        if(boardId.equals("primary", true)) {
            for(board in boards.values) {
                board.switchBoardToPrimary()
            }
            return
        }

        for(board in boards.values) {
            board.switchBoard(boardId)
        }
    }

    override fun updateAllBoardLine(boardId: String, lineId: String) {
        val matchingBoards = boards.values.filter { it.id.equals(boardId, true) }
        if(matchingBoards.isEmpty()) {
            return
        }

        for(board in matchingBoards) {
            board.updateLineById(lineId)
        }
    }
}