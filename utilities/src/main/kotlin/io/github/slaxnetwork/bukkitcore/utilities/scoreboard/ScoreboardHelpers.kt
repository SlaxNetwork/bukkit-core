package io.github.slaxnetwork.bukkitcore.utilities.scoreboard

import io.github.slaxnetwork.bukkitcore.scoreboard.BoardComponent
import io.github.slaxnetwork.bukkitcore.scoreboard.BoardLine

fun boardComponent(comp: BoardComponent.() -> Unit): BoardComponent {
    return BoardComponent().apply(comp)
}

fun boardLine(id: String, line: Int, comp: BoardComponent.() -> Unit): BoardLine {
    return BoardLine(id, line, true, BoardComponent().apply(comp))
}

fun boardLine(id: String, line: Int, enabled: Boolean, comp: BoardComponent.() -> Unit): BoardLine {
    return BoardLine(id, line, enabled, BoardComponent().apply(comp))
}