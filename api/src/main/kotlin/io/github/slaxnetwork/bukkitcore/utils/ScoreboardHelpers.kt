package io.github.slaxnetwork.bukkitcore.utils

import io.github.slaxnetwork.bukkitcore.scoreboard.BoardComponent
import io.github.slaxnetwork.bukkitcore.scoreboard.BoardLine

// TODO: 3/23/2023 fix these are not being compiled into target jar since it's marked as compileOnly

fun boardLine(id: String, line: Int, comp: BoardComponent.() -> Unit): BoardLine {
    return BoardLine(id, line, BoardComponent().apply(comp))
}

fun boardComponent(comp: BoardComponent.() -> Unit): BoardComponent {
    return BoardComponent().apply(comp)
}