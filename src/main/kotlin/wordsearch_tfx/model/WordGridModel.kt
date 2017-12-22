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
    /*
    operation WordGridModel.placeWordSpecific(word, row, column, direction) {
        // Make sure that the word is in the WordGridEntry array
        var wge = getGridEntryByWord(word);

        if (wge == null) {
            // Word not found in word lists
            return false;
        }
        else {
            if (wge.placed) {
                // Word is already placed
                return false;
            }
        }

        // Check to make sure that the word may be placed there
        if (not canPlaceWordSpecific(word, row, column, direction,
        DEFAULT_LOOK:WordGridRect)) {
            return false;
        }

        // Word can be placed, so place it now
        wge.row = row;
        wge.column = column;
        wge.direction = direction;
        placeWordGridEntry(wge);

        delete unplacedGridEntries[w | w == wge];
        insert wge into placedGridEntries;
        wge.placed = true;

        return true;
    }
    */

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
    /*
    operation WordGridModel.canPlaceWordSpecific(word, row, column, direction,
    cellAppearance) {
        var xPos = column;
        var yPos = row;

        // amount to increment in each direction for subsequent letters
        var xIncr = 0;
        var yIncr = 0;

        var canPlaceWord = true;

        // Check to make sure that the word may be placed there
        xIncr = getXIncr(direction);
        yIncr = getYIncr(direction);

        // Make all cells in the grid have the default appearance
        highlightWordsOnCell(NO_CELL:Integer);

        // Make sure that the word can be placed
        for (i in [0.. word.length() - 1]) {
            if (xPos > columns - 1 or yPos > rows - 1 or xPos < 0 or yPos <0) {
                // The word can't be placed because one of the letters is off the grid
                canPlaceWord = false;
                break;
            }
            // See if the letter being placed is either a space or the same letter
            else if ((gridCells[yPos * columns + xPos].cellLetter <> SPACE:String) and
                    (gridCells[yPos * columns + xPos].cellLetter <> word.substring(i, i+1))) {
                // The word can't be placed because of a conflict with another
                // letter on the grid
                canPlaceWord = false;
            }
            if (cellAppearance == DRAGGING_LOOK:WordGridRect) {
                gridCells[yPos * columns + xPos].appearance = DRAGGING_LOOK;
            }
            else if (cellAppearance == CANT_DROP_LOOK:WordGridRect) {
                gridCells[yPos * columns + xPos].appearance = CANT_DROP_LOOK;
            }
            else if (i == 0) {
                // This is the first letter of the word
                gridCells[yPos * columns + xPos].appearance = DEFAULT_FIRST_LETTER_LOOK;
            }
            else {
                gridCells[yPos * columns + xPos].appearance = DEFAULT_LOOK;
            }
            xPos += xIncr;
            yPos += yIncr;
        }
        return canPlaceWord;
    }
    */

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
    /*
    operation WordGridModel.getXIncr(direction) {
        var xIncr:Integer = 1;
        if (direction == VERT:WordOrientation.id) {
            xIncr = 0;
        }
        return xIncr;
    }
    */

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
    /*
    operation WordGridModel.getYIncr(direction) {
        var yIncr:Integer = 1;
        if (direction == HORIZ:WordOrientation.id) {
            yIncr = 0;
        }
        else if (direction == DIAG_UP:WordOrientation.id) {
            yIncr = -1;
        }
        return yIncr;
    }
    */


    /**
     * This method refreshes the grid with the words that have already been placed.
     * This would be called, for example, when the user requests that
     * "fill letters" be shown, because after the grid is filled with
     * fill letters, the placed words need to be put back on the grid.
     */
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

            // Associate this WordGridEntry with the cell on the grid view
            //insert wge into gridCells[yPos * columns + xPos].wordEntries;
            wgCells.get(yPos * cols + xPos).wordItems.add(wordItem)

            xPos += xIncr;
            yPos += yIncr;
        }
    }

    /*
    operation WordGridModel.placeWordGridEntry(wge) {
        var xPos = wge.column;
        var yPos = wge.row;
        var xIncr = getXIncr(wge.direction);
        var yIncr = getYIncr(wge.direction);
        var word = wge.word;
        for (i in [0.. word.length()- 1]) {
            gridCells[yPos * columns + xPos].cellLetter = word.substring(i, i + 1);

            // Associate this WordGridEntry with the cell on the grid view
            insert wge into gridCells[yPos * columns + xPos].wordEntries;

            xPos += xIncr;
            yPos += yIncr;
        }
    }
    */


}