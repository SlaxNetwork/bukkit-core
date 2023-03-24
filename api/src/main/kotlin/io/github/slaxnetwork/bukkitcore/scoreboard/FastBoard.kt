package io.github.slaxnetwork.bukkitcore.scoreboard

interface FastBoard {
    /**
     * Scoreboard id.
     */
    val id: String

    /**
     * String representation of the scoreboard title.
     */
    val title: String

    /**
     * Whether the scoreboard has been deleted.
     */
    val isDeleted: Boolean

    /**
     * Update the title of a scoreboard.
     * @param title New title.
     */
    fun updateTitle(title: BoardComponent) // added

    /**
     * Get the string representation of each line on the scoreboard.
     * @return List of every line as a string.
     */
    fun getLines(): List<String>

    /**
     * Get the string representation of a specific line on the scoreboard.
     * @return Line as a string.
     */
    fun getLine(line: Int): String

    /**
     * Bulk update every line on a scoreboard.
     * @param lines Every new line to add.
     */
    fun updateLines(vararg lines: BoardLine) // added

    /**
     * Bulk update every line on a scoreboard.
     * @param lines Every new line to add.
     */
    fun updateLines(lines: Collection<BoardLine>) // added

    /**
     * Update a specific scoreboard line.
     * @param boardLine Specific board line to update with new attributes.
     */
    fun updateLine(boardLine: BoardLine) // added

    /**
     * Update a specific scoreboard line based on its index.
     * @param index Index to update.
     */
    fun updateLineByIndex(index: Int) // added

    /**
     * Update a specific scoreboard line based on its line id.
     * @param lineId Line to update.
     */
    fun updateLineById(lineId: String) // added

    fun refresh()

    /**
     * Remove a specific line from the scoreboard.
     * @param line Index to delete.
     */
    fun removeLine(line: Int)

    fun enableLine(lineId: String)

    fun enableLine(index: Int)

    fun disableLine(lineId: String)

    fun disableLine(index: Int)

    /**
     * Delete the scoreboard for the player.
     */
    fun delete()

    /**
     * @return Amount of scoreboard lines.
     */
    fun size(): Int
}