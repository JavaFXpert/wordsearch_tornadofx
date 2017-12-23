package wordsearch_tfx.view

import javafx.animation.Interpolator
import javafx.geometry.Point2D
import javafx.scene.layout.Pane
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridCell
import wordsearch_tfx.model.WordGridModel

class WordGridView : View() {
    private val wgModel: WordGridModel by inject()
    private val CELL_WIDTH_HEIGHT = 30.0

    override val root = borderpane {
        val wgCellNodes = Pane()
        top = text("My Word Search Puzzle").setId(Styles.title)
        center = pane {
            for (row in 0 until wgModel.numRows) {
                for (col in 0 until wgModel.numCols) {
                    val wgCell = WordGridCell()
                    wgModel.addWordGridCell(wgCell)

                    //TODO: How best to express this declaratively?
                    val wgCellNode = WordGridCellNode(CELL_WIDTH_HEIGHT, wgCell)
                    wgCellNode.move(0.1.seconds,
                            Point2D(col * CELL_WIDTH_HEIGHT + 7,
                                    row * CELL_WIDTH_HEIGHT + 22), Interpolator.LINEAR)
                    wgCellNode.setOnMouseEntered {
                        wgModel.highlightWordsOnCell(row * wgModel.numCols + col )
                    }
                    wgCellNode.setOnMouseExited {
                        wgModel.highlightWordsOnCell(wgModel.NO_CELL)
                    }
                    wgCellNodes.add(wgCellNode)
                }
            }
            add(wgCellNodes)
        }
    }
}
