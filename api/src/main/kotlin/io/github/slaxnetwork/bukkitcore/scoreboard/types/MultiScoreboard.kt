package io.github.slaxnetwork.bukkitcore.scoreboard.types

import io.github.slaxnetwork.bukkitcore.scoreboard.BoardLine

interface MultiScoreboard : SimpleScoreboard {
    val subBoards: Map<String, List<BoardLine>>

    fun identifyLines(boardId: String): Map<String, BoardLine>? {
        return subBoards[boardId]?.associateBy { it.id }
    }
}