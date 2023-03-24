package io.github.slaxnetwork.bukkitcore.scoreboard

data class BoardLine(
    val id: String,
    var line: Int,
    var enabled: Boolean = true,
    val component: BoardComponent
)