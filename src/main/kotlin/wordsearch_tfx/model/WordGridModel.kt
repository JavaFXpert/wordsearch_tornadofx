/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wordsearch_tfx.model

import javafx.beans.property.SimpleBooleanProperty
import wordsearch_tfx.model.WordOrientation.*
import tornadofx.*
import wordsearch_tfx.controller.WordStore
import wordsearch_tfx.view.CellAppearance
import wordsearch_tfx.view.CellAppearance.*

class WordGridModel(): ViewModel() {
    private val wordStore = find(WordStore::class)

    /*
     Constant that indicates that an operation
     pertains to no cell.  Used as an argument to highlightWordsOnCell()
    */
    val NO_CELL:Int = -1;

    val numRows: Int = 12
    val numCols: Int = 12
    val numWordOrientations = 4

    val fillLettersOnGrid = SimpleBooleanProperty(false)

    init {
        fillLettersOnGrid.onChange {
            if (it) {
                clearGridCells()
                copyFillLettersToGrid()
                refreshWordsOnGrid()
            }
            else {
                clearGridCells()
                refreshWordsOnGrid()
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

    fun addWordGridCell(wgCell: WordGridCell) {
        wgCells.add(wgCell)
    }

    /**
     * Places a word on the grid with no specified location or orientation.
     * Beginning with a random row, column, and orientation, it tries every
     * available position for a word before giving up and returning false.
     * If successful it places the word and returns true.
     */
    fun placeWord(word: WordItem): Boolean {
        var success: Boolean
        val startingRow = (Math.random() * numRows).toInt()
        val startingCol = (Math.random() * numCols).toInt()
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val startingOrientId = (Math.random() * numWordOrientations).toInt()
                for (d in 0 until numWordOrientations) {
                    val orientId = (startingOrientId + d) % numWordOrientations

                    val wordOrientation = getWordOrientationById(orientId)
                    success = placeWordSpecific(word,
                            (startingRow + row) % numRows,
                            (startingCol + col) % numCols,
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
        if (!canPlaceWordSpecific(wordItem.text, row, col, orientation, DEFAULT_LOOK)) {
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
    fun canPlaceWordSpecific(word: String, row: Int, col: Int,
                                     orientation: WordOrientation,
                                     cellAppearance: CellAppearance): Boolean {
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
            if (xPos > numCols - 1 || yPos > numRows - 1 || xPos < 0 || yPos <0) {
                // The word can't be placed because one of the letters is off the grid
                canPlaceWord = false
                break
            }
            // See if the letter being placed is either a space or the same letter
            else if ((wgCells.get(yPos * numCols + xPos).cellLetter.get() != " ") &&
                    (wgCells.get(yPos * numCols + xPos).cellLetter.get() != word.substring(i, i+1))) {
                // The word can't be placed because of a conflict with another
                // letter on the grid
                canPlaceWord = false;
            }

            if (cellAppearance == DRAGGING_LOOK) {
                wgCells.get(yPos * numCols + xPos).appearance.set(DRAGGING_LOOK)
            }
            else if (cellAppearance == CANT_DROP_LOOK) {
                wgCells.get(yPos * numCols + xPos).appearance.set(CANT_DROP_LOOK)
            }
            else if (i == 0) {
                // This is the first letter of the word
                wgCells.get(yPos * numCols + xPos).appearance.set(DEFAULT_FIRST_LETTER_LOOK)
            }
            else {
                wgCells.get(yPos * numCols + xPos).appearance.set(DEFAULT_LOOK)
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
            val wgCell = wgCells.get(yPos * numCols + xPos)
            wgCell.cellLetter.set(text.substring(idx, idx + 1))

            /*
             Associate this WordItem with the cell on the grid view,
             protecting against adding the same item twice
              */
            if (wgCell.wordItems.indexOf(wordItem) == -1) {
                wgCell.wordItems.add(wordItem)
            }

            xPos += xIncr;
            yPos += yIncr;
        }
    }

    /**
     * Unlaces a word from the grid. This doesn't remove the word from the word
     * list. It only unplaces it from the grid, marking it as not placed.
     */
    fun unplaceWord(wordItem: WordItem): Boolean {
        var xPos = wordItem.gridCol;
        var yPos = wordItem.gridRow;
        var xIncr = getXIncr(wordItem.wordOrientation);
        var yIncr = getYIncr(wordItem.wordOrientation);

        val text = wordItem.text
        for (idx in 0 until text.length) {
            val wgCell = wgCells.get(yPos * numCols + xPos)
            wgCell.cellLetter.set(" ")

            // Disassociate this WordItem with the cell on the grid view
            wgCell.wordItems.remove(wordItem)

            xPos += xIncr
            yPos += yIncr
        }
        return true
    }

    /**
     * Set the highlightCell attribute of the model for every letter of
     * every word that has one if its letters in a given cell.
     */
    fun highlightWordsOnCell(cellNum: Int) {

        gridCellsDefaultAppearance()

        if (cellNum != NO_CELL && wgCells.size > cellNum) {
            val wgCell = wgCells.get(cellNum)

            for (wordItem in wgCell.wordItems) {
                var xPos = wordItem.gridCol;
                var yPos = wordItem.gridRow;
                val xIncr = getXIncr(wordItem.wordOrientation);
                val yIncr = getYIncr(wordItem.wordOrientation);
                for (idx in 0 until wordItem.text.length) {
                    if (idx == 0) {
                        wgCells.get(yPos * numCols + xPos ).appearance.set(SELECTED_FIRST_LETTER_LOOK)
                    }
                    else {
                        wgCells.get(yPos * numCols + xPos ).appearance.set(SELECTED_LOOK)
                    }
                    xPos += xIncr;
                    yPos += yIncr;
                }
            }
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
     * This method refreshes the grid with the words that have already been placed.
     * This would be called, for example, when the user requests that
     * "fill letters" be shown, because after the grid is filled with
     * fill letters, the placed words need to be put back on the grid.
     */
    fun refreshWordsOnGrid() {
        val itemsPlaced = wordStore.words.items.filtered{it.placed}

        for (wordItem in itemsPlaced) {
            placeWordGridEntry(wordItem)
        }
    }

    /**
     * Fills the grid (two-dimensional array that stores the word search puzzle
     * letters) with spaces
     */
    fun clearGridCells() {
        for (wgCell in wgCells) {
            wgCell.cellLetter.set(" ")
            wgCell.appearance.set(DEFAULT_LOOK)
        }
    }

    /**
     * Makes all cell have default appearance
     */
    fun gridCellsDefaultAppearance() {
        for (wgCell in wgCells) {
            wgCell.appearance.set(DEFAULT_LOOK)
        }
    }


}