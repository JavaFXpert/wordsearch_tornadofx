package wordsearch_tfx.view

import javafx.animation.Interpolator
import javafx.geometry.Point2D
import javafx.scene.Cursor.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridCell
import wordsearch_tfx.model.WordGridModel
import wordsearch_tfx.model.WordItem
import wordsearch_tfx.view.CellAppearance.*

class WordGridView : View() {
    private val wgModel: WordGridModel by inject()
    private val CELL_WIDTH_HEIGHT = 30.0

    // For dragging words around on the grid:
    // The row and column of the first letter of the word to be dragged
    private var dragOrigRow: Int = 0
    private var dragOrigColumn: Int = 0

    // The row and column of the cell to which the first letter of the word
    // is being dragged.
    private var dragToRow: Int = 0
    private var dragToColumn: Int = 0

    // The word grid entry of the word being dragged
    private var dragOrigWge = WordItem("")

    // This holds the state of whether a word is being dragged
    private var dragging: Boolean = false

    override val root = borderpane {
        val wgCellNodes = Pane()
        top = text("My Word Search Puzzle").setId(Styles.title)
        center = pane {
            for (row in 0 until wgModel.numRows) {
                for (col in 0 until wgModel.numCols) {
                    var wgCell = WordGridCell()
                    wgModel.addWordGridCell(wgCell)

                    val wgCellNode = WordGridCellNode(CELL_WIDTH_HEIGHT, wgCell)
                    wgCellNode.move(0.1.seconds,
                            Point2D(col * CELL_WIDTH_HEIGHT + 7,
                                    row * CELL_WIDTH_HEIGHT + 22), Interpolator.LINEAR)

                    wgCellNode.setOnMouseEntered {
                        wgModel.highlightWordsOnCell(row * wgModel.numCols + col )
                    }
                    wgCellNode.setOnMouseMoved {
                        wgModel.highlightWordsOnCell(row * wgModel.numCols + col )
                    }
                    wgCellNode.setOnMouseExited {
                        wgModel.highlightWordsOnCell(wgModel.NO_CELL)
                    }
                    wgCellNode.setOnMousePressed {
                        if (!wgModel.fillLettersOnGrid.get()) {
                            setCursor(DEFAULT)
                            dragging = false
                            wgCell = wgModel.wgCells.get(row * wgModel.numCols + col)
                            if (wgCell.wordItems.size > 0) {
                                dragOrigWge = wgCell.wordItems[0]
                                if (dragOrigWge.gridRow == row &&
                                        dragOrigWge.gridCol == col) {
                                    dragOrigRow = row
                                    dragOrigColumn = col
                                    dragToRow = row
                                    dragToColumn = col
                                    dragging = true
                                }
                            }
                        }
                    }
                    wgCellNode.setOnMouseDragged {
                        // If the fill letters aren't on the grid, use the CanvasMouseEvent
                        // to know where the user is dragging the mouse.  Give feedback to
                        // the user as to whether the word can be placed where it is
                        // currently being dragged.
                        if (!wgModel.fillLettersOnGrid.get()) {
                            if (dragging) {
                                wgModel.gridCellsDefaultAppearance()
                                dragToRow = ((it.y + (dragOrigRow + 0.75) * CELL_WIDTH_HEIGHT) / CELL_WIDTH_HEIGHT).toInt()
                                dragToColumn = ((it.x + (dragOrigColumn + 0.25) * CELL_WIDTH_HEIGHT) / CELL_WIDTH_HEIGHT).toInt()

                                // See if the word can be placed, giving the cells under
                                // consideration the "dragged" look.
                                if (!wgModel.canPlaceWordSpecific(
                                    dragOrigWge.text,
                                    dragToRow,
                                    dragToColumn,
                                    dragOrigWge.wordOrientation,
                                    DRAGGING_LOOK)) {
                                    // The word can't be placed, so call the same method, passing
                                    // an argument that causes the cells to have a "can't drop look"
                                    wgModel.canPlaceWordSpecific(dragOrigWge.text,
                                            dragToRow,
                                            dragToColumn,
                                            dragOrigWge.wordOrientation,
                                            CANT_DROP_LOOK)
                                }
                            }
                        }
                    }
                    wgCellNode.setOnMouseReleased {
                        // If the fill letters aren't on the grid, use the CanvasMouseEvent
                        // to know where the user is dragging the mouse.  Give feedback to
                        // the user as to whether the word can be placed where it is
                        // currently being dragged.
                        if (!wgModel.fillLettersOnGrid.get()) {
                            if (dragging) {
                                dragging = false

                                if (wgModel.canPlaceWordSpecific(
                                    dragOrigWge.text,
                                    dragToRow,
                                    dragToColumn,
                                    dragOrigWge.wordOrientation,
                                    DEFAULT_LOOK)) {

                                    if (wgModel.unplaceWord(dragOrigWge)) {
                                        wgModel.placeWordSpecific(dragOrigWge,
                                                dragToRow,
                                                dragToColumn,
                                                dragOrigWge.wordOrientation)
                                    }
                                }
                            }

                            wgModel.clearGridCells()
                            wgModel.refreshWordsOnGrid()
                        }
                    }
                    wgCellNodes.add(wgCellNode)
                }
            }
            add(wgCellNodes)
        }
    }
}
