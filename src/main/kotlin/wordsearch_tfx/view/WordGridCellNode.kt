package wordsearch_tfx.view

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridCell

class WordGridCellNode(widthHeight: Double, wgCell: WordGridCell): Pane() {
    init {
        replaceChildren {
            rectangle(-7.0, -22.0, widthHeight, widthHeight) {
                stroke = Color.BLACK
                fill = Color.TRANSPARENT
            }
            text(" ").addClass(Styles.gridCellLetter).bind(wgCell.cellLetter)
        }
    }
}