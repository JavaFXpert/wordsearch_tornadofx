package wordsearch_tfx.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableBooleanValue
import wordsearch_tfx.model.WordOrientation.*
import tornadofx.*

class WordGridModel(): ViewModel() {
    val rows: Int = 9
    val cols: Int = 9
    val numWordOrientations = 4

    val fillLettersOnGrid = SimpleBooleanProperty(false)

    /*
    //TODO: See if there is a variation of this that will work when UI controls are bidirectionally bound
    val fillLettersOnGrid: SimpleBooleanProperty = SimpleBooleanProperty(false).onChange {
        copyFillLettersToGrid()
    }
    */

    init {
        //TODO: Ascertain more Kotlin/TornadoFX-ish way to accomplish this when UI controls are bidirectionally
        //      bound to fillLettersOnGrid.
        fillLettersOnGrid.addListener{
            e -> run {
                if (e is BooleanProperty && e.getValue()) {
                    clearGridCells()
                    copyFillLettersToGrid()
                }
                else {
                    clearGridCells()
                }
            }
        }
    }

    val wgCells: ArrayList<WordGridCell> = ArrayList()

    private fun getWordOrientationById(id: Int) =
        when (id) {
            0 -> HORIZ
            1 -> DIAG_DOWN
            2 -> VERT
            3 -> DIAG_UP
            else -> throw Exception();
        }

    //TODO: Is there a more Kotlin-ish way to do this than to expose an add method?
    fun addWordGridCell(wgCell: WordGridCell) {
        wgCells.add(wgCell)
    }

    /**
     * Fills the grid (two-dimensional array that stores the word search puzzle
     * letters) with spaces
     */
    private fun clearGridCells() {
        for (wgCell in wgCells) {
            wgCell.cellLetter.set(" ");
        }
    }

    /**
     * Copies the randomly generated fill letters from the array in which they are
     * stored into the array that stores the word search puzzle letters.
     */
    private fun copyFillLettersToGrid() {
        for (wgCell in wgCells) {
            //wgCell.cellLetter = wgCell.fillLetter
            wgCell.cellLetter.set(wgCell.fillLetter.get())
        }
    }

    /**
     * Places a word on the grid with no specified location or orientation.
     * Beginning with a random row, column, and orientation, it tries every
     * available position for a word before giving up and returning false.
     * If successful it places the word and returns true.
     */
    //TODO: Perhaps throw an exception if unsuccessful instead of returning false
    fun placeWord(word: WordItem): Boolean {
        var success: Boolean
        val startingRow = (Math.random() * rows).toInt()
        val startingCol = (Math.random() * cols).toInt()
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val startingOrientId = Math.random().toInt() * numWordOrientations
                for (d in 0 until numWordOrientations) {
                    val orientId = (startingOrientId + d) % numWordOrientations

                    //TODO: get WordOrientation by Id
                    val wordOrientation = getWordOrientationById(orientId)
                    success = placeWordSpecific(word,
                            (startingRow + row) % rows,
                            (startingCol + col) % cols,
                            wordOrientation);
                    if (success) {
                        return true;
                    }
                }
            }
        }
        return false
    }

    /**
     * Places a word on the grid at a specified location and orientation. The word
     * must already be in the word list. If the word is successfully placed this
     * method sets the internal state of the associate WordItem with the row,
     * column, orientation, and the fact that it is now placed.
     */
    fun placeWordSpecific(wordItem: WordItem, row: Int, col: Int,
                          orientation: WordOrientation): Boolean {

        // Check to make sure that the word may be placed there
        //TODO: Pass an additional (appearance) argument
        if (!canPlaceWordSpecific(wordItem.text, row, col, orientation)) {
            return false;
        }
        else {
            // Word can be placed, so place it now
            wordItem.gridRow = row
            wordItem.gridCol = col
            wordItem.wordOrientation = orientation;
            placeWordGridEntry(wordItem)
            return true
        }
    }

    /**
     * Checks to see if a word can be placed on the grid at a specified location
     * and orientation.  It also specifies the appearance state that the cells
     * should have.
     */
    //TODO: Implement the appearance state including a cellAppearance parameter
    private fun canPlaceWordSpecific(word: String, row: Int, col: Int,
                                     orientation: WordOrientation): Boolean {
        var xPos = col
        var yPos = row

        // amount to increment in each direction for subsequent letters
        var xIncr: Int
        var yIncr: Int

        var canPlaceWord = true;

        // Check to make sure that the word may be placed there
        xIncr = getXIncr(orientation);
        yIncr = getYIncr(orientation);

        // Make sure that the word can be placed
        for (i in 0 until word.length) {
            if (xPos > cols - 1 || yPos > rows - 1 || xPos < 0 || yPos <0) {
                // The word can't be placed because one of the letters is off the grid
                canPlaceWord = false;
                break;
            }
            // See if the letter being placed is either a space or the same letter
            else if ((wgCells.get(yPos * cols + xPos).cellLetter.get() != " ") &&
                    (wgCells.get(yPos * cols + xPos).cellLetter.get() != word.substring(i, i+1))) {
                // The word can't be placed because of a conflict with another
                // letter on the grid
                canPlaceWord = false;
            }
            xPos += xIncr;
            yPos += yIncr;
        }
        return canPlaceWord
    }

    /**
     * This method calculates the number that should be added to the column in
     * which the previous letter was placed, in order to calculate the column in
     * which next letter should be placed.  This is based upon the direction that
     * the word is to be placed. For example, this method would return 1 if the word
     * is to be placed horizontally, but 0 if it is to be placed vertically.
     */
    private fun getXIncr(orientation: WordOrientation): Int {
        var xIncr = 1;
        if (orientation == VERT) {
            xIncr = 0
        }
        return xIncr
    }

    /**
     * This method calculates the number that should be added to the row in
     * which the previous letter was placed, in order to calculate where the
     * next letter should be placed. For example, this method would return 0 if
     * the word is to be placed horizontally, but 1 if it is to be placed vertically.
     */
    private fun getYIncr(orientation: WordOrientation): Int {
        var yIncr = 1;
        if (orientation == HORIZ) {
            yIncr = 0
        }
        else if (orientation == DIAG_UP) {
            yIncr = -1
        }
        return yIncr
    }

    /**
     * This method refreshes the grid with the words that have already been placed.
     * This would be called, for example, when the user requests that
     * "fill letters" be shown, because after the grid is filled with
     * fill letters, the placed words need to be put back on the grid.
     */
    private fun refreshWordsOnGrid() {
        //TODO: Ascertain best way to get collection of place words from WordStore
    }

    /*
    operation WordGridModel.refreshWordsOnGrid() {
        for (i in [0..sizeof placedGridEntries - 1]) {
            placeWordGridEntry(placedGridEntries[i]);
        }
    }
    */

    /**
     * This method takes a WordGridEntry and places each letter in the word onto
     * the grid, according to the position and direction stored in the WordGridEntry
     */
    private fun placeWordGridEntry(wordItem: WordItem) {
        var xPos = wordItem.gridCol
        var yPos = wordItem.gridRow
        var xIncr = getXIncr(wordItem.wordOrientation)
        var yIncr = getYIncr(wordItem.wordOrientation)
        val text = wordItem.text
        for (idx in 0 until text.length) {
            wgCells.get(yPos * cols + xPos).cellLetter.set(text.substring(idx, idx + 1))

            // Associate this WordItem with the cell on the grid view
            wgCells.get(yPos * cols + xPos).wordItems.add(wordItem)

            xPos += xIncr;
            yPos += yIncr;
        }
    }

    /**
     * Unlaces a word from the grid. This doesn't remove the word from the word
     * list. It only unplaces it from the grid, marking it as not placed.
     */
    fun unplaceWord(wordItem: WordItem) {
        println("In unplaceWord function for word: ${wordItem.text} (not implemented yet)")
        //TODO: Implement this function
    }

    /*
    operation WordGridModel.unplaceWord(word) {
        var wge = getGridEntryByWord(word);
        if (wge == null) {
            // Word not found in WordGridModel word list
            return false;
        }
        else {
            if (not wge.placed) {
                // Word is already unplaced
                return false;
            }
        }
        var xPos = wge.column;
        var yPos = wge.row;
        var xIncr = getXIncr(wge.direction);
        var yIncr = getYIncr(wge.direction);

        var i = 0;
        while (i < word.length()) {
            gridCells[yPos * columns + xPos].cellLetter = SPACE:String;

            // Dissasociate this WordGridEntry with the cell on the grid view
            var wges = gridCells[yPos * columns + xPos].wordEntries;
            delete wges[w | w == wge];

            xPos += xIncr;
            yPos += yIncr;
            i++;
        }
        insert wge into unplacedGridEntries;
        delete placedGridEntries[w | w == wge];
        wge.placed = false;

        initializeGrid();
        refreshWordsOnGrid();
        return true;
    }
    */

}