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
     * The current board id being used.
     * Primary if no sub-board is being used.
     */
    val currentBoardId: String

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

    /**
     * Refresh all lines on the current scoreboard.
     */
    fun refresh() // added

    /**
     * Switch the scoreboard to the primary one.
     */
    fun switchBoardToPrimary() // added

    /**
     * Switch the scoreboard to a sub-board.
     * @param boardId Sub-board id.
     */
    fun switchBoard(boardId: String) // added

    /**
     * Remove a specific line from the scoreboard.
     * @param line Index to delete.
     */
    fun removeLine(line: Int) // added

    /**
     * Enable a line on the scoreboard based on its id.
     * @param lineId Line to enable.
     */
    fun enableLine(lineId: String) // added

    /**
     * Disable a line on the scoreboard based on its id.
     * @param lineId Line to disable.
     */
    fun disableLine(lineId: String) // added

    /**
     * Delete the scoreboard for the player.
     */
    fun delete()

    /**
     * @return Amount of scoreboard lines.
     */
    fun size(): Int
}