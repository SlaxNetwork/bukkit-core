package io.github.slaxnetwork.bukkitcore.utilities.scoreboard

import io.github.slaxnetwork.bukkitcore.scoreboard.BoardComponent
import io.github.slaxnetwork.bukkitcore.scoreboard.BoardLine

fun boardComponent(comp: BoardComponent.() -> Unit): BoardComponent {
    return BoardComponent().apply(comp)
}

fun boardLine(id: String, priority: Int, comp: BoardComponent.() -> Unit): BoardLine {
    return BoardLine(id, priority, true, BoardComponent().apply(comp))
}

fun boardLine(id: String, priority: Int, enabled: Boolean, comp: BoardComponent.() -> Unit): BoardLine {
    return BoardLine(id, priority, enabled, BoardComponent().apply(comp))
}