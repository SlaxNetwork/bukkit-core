package io.github.slaxnetwork.bukkitcore.scoreboard

interface SimpleScoreboard {
    val title: BoardComponent

    val lines: List<BoardLine>

    val identifiedLines: Map<String, BoardLine>
        get() = lines.associateBy { it.id }
}