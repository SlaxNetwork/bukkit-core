package io.github.slaxnetwork.bukkitcore.scoreboard.types

import io.github.slaxnetwork.bukkitcore.scoreboard.BoardComponent
import io.github.slaxnetwork.bukkitcore.scoreboard.BoardLine

interface SimpleScoreboard {
    val title: BoardComponent

    val lines: List<BoardLine>

    fun identifyLines(): Map<String, BoardLine> {
        return lines.associateBy { it.id }
    }
}