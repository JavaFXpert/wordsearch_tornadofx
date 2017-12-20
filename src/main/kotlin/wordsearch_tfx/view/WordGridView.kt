package wordsearch_tfx.view

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridModel

class WordGridView : View() {
    private val wgModel: WordGridModel by inject()
    private val CELL_WIDTH_HEIGHT = 30.0
    private val wgCellFrags: Pane = Pane()
    private val textLetters: Array<Text> = emptyArray()

    init {
        for (row in 0..wgModel.rows) {
            for (col in 0..wgModel.cols) {
                wgCellFrags.add(WordGridCellFrag(row, col, CELL_WIDTH_HEIGHT))
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
