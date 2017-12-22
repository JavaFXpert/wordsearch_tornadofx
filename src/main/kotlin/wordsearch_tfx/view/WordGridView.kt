package wordsearch_tfx.view

import javafx.scene.layout.Pane
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridCell
import wordsearch_tfx.model.WordGridModel

class WordGridView : View() {
    private val wgModel: WordGridModel by inject()
    private val CELL_WIDTH_HEIGHT = 30.0
    private val wgCellFrags: Pane = Pane()

    init {
        for (row in 0 until wgModel.rows) {
            for (col in 0 until wgModel.cols) {
                val wgCell = WordGridCell()
                wgCellFrags.add(WordGridCellFrag(row, col, CELL_WIDTH_HEIGHT, wgCell))
                wgModel.addWordGridCell(wgCell)
            }
        }

    }

    override val root = borderpane {
        top = text("My Word Search Puzzle").setId(Styles.title)
        center = pane {
            add(wgCellFrags)
        }
    }
}
