package io.github.slaxnetwork.bukkitcore.scoreboard

interface FastBoard {
    val id: String

    val title: String

    val isDeleted: Boolean

    fun updateTitle(title: BoardComponent) // added

    fun getLines(): List<String>

    fun getLine(line: Int): String

    fun updateLines(vararg lines: BoardLine) // added

    fun updateLines(lines: Collection<BoardLine>) // added

    fun updateLine(boardLine: BoardLine) // added

    fun removeLine(line: Int)

    fun delete()

    fun size(): Int
}