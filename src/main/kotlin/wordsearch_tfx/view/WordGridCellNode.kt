package wordsearch_tfx.view

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridCell

class WordGridCellNode(widthHeight: Double, wgCell: WordGridCell): Pane() {
    var rect = Rectangle()

    init {
        replaceChildren {
            rect = rectangle(-7.0, -22.0, widthHeight, widthHeight) {
                stroke = Color.BLACK
                fill = Color.TRANSPARENT
            }
            text(" ").addClass(Styles.gridCellLetter).bind(wgCell.cellLetter)
        }

        wgCell.appearance.onChange {
            if (it is CellAppearance && it == CellAppearance.DEFAULT_LOOK) {
                rect.fill = Color.TRANSPARENT
            }
            else if (it is CellAppearance && it == CellAppearance.SELECTED_LOOK) {
                rect.fill = Color.YELLOW
            }
            else if (it is CellAppearance && it == CellAppearance.SELECTED_FIRST_LETTER_LOOK) {
                rect.fill = Color.YELLOW
            }
        }
    }
}