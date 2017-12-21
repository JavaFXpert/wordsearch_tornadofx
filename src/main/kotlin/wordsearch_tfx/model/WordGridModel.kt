package wordsearch_tfx.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ObservableBooleanValue
import tornadofx.*

class WordGridModel(): ViewModel() {
    val rows: Int = 9
    val cols: Int = 9

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

    /*
    operation WordGridModel.initializeGrid() {
        if (sizeof gridCells == 0) {
            for (i in [0.. (rows * columns) - 1]) {
                insert WordGridCell{} into gridCells;
            }
        }
        else {
            for (i in [0.. sizeof gridCells - 1]) {
                gridCells[i].cellLetter = SPACE:String;

                gridCells[i].wordEntries = [];
            }
        }
    }
    */



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
    /*
    operation WordGridModel.copyFillLettersToGrid() {
        for (i in [0.. sizeof gridCells - 1]) {
            gridCells[i].cellLetter = gridCells[i].fillLetter;
        }
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

}