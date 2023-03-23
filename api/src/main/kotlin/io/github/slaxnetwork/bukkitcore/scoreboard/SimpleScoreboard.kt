package io.github.slaxnetwork.bukkitcore.scoreboard

interface SimpleScoreboard {
    val title: BoardComponent

    val lines: List<BoardLine>
}