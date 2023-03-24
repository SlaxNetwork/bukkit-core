package io.github.slaxnetwork.bukkitcore.scoreboard

data class BoardLine(
    val id: String,
    var priority: Int,
    var enabled: Boolean = true,
    val component: BoardComponent
)