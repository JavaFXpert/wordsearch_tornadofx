package wordsearch_tfx.view

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridModel

class WordGridView : View() {
    private val wgModel: WordGridModel by inject()
    private val CELL_WIDTH_HEIGHT = 30.0

    override val root = pane {
        for (row in 0..wgModel.rows) {
            for (col in 0..wgModel.cols) {
                add(WordGridRect(row, col, CELL_WIDTH_HEIGHT))
            }
        }
    }
}
