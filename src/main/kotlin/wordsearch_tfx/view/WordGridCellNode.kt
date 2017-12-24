package wordsearch_tfx.view

import javafx.scene.Cursor
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
            if (it is CellAppearance && it == CellAppearance.SELECTED_LOOK) {
                rect.strokeWidth = 2.0
                rect.stroke = Color.BLACK
                rect.fill = Color.YELLOW
                cursor = Cursor.DEFAULT
            }
            else if (it is CellAppearance && it == CellAppearance.SELECTED_FIRST_LETTER_LOOK) {
                rect.strokeWidth = 2.0
                rect.stroke = Color.BLACK
                rect.fill = Color.YELLOW
                cursor = Cursor.HAND
            }
            else if (it is CellAppearance && it == CellAppearance.DRAGGING_LOOK) {
                rect.strokeWidth = 1.0
                rect.stroke = Color.CYAN
                rect.fill = Color.CYAN
                cursor = Cursor.HAND
            }
            else if (it is CellAppearance && it == CellAppearance.CANT_DROP_LOOK) {
                rect.strokeWidth = 1.0
                rect.stroke = Color.RED
                rect.fill = Color.RED
                cursor = Cursor.MOVE
            }
            else if (it is CellAppearance && it == CellAppearance.DEFAULT_FIRST_LETTER_LOOK) {
                rect.strokeWidth = 1.0
                rect.stroke = Color.BLACK
                rect.fill = Color.TRANSPARENT
                cursor = Cursor.HAND
            }
            else if (it is CellAppearance && it == CellAppearance.DEFAULT_LOOK) {
                rect.strokeWidth = 1.0
                rect.stroke = Color.BLACK
                rect.fill = Color.TRANSPARENT
                cursor = Cursor.DEFAULT
            }
        }
    }
}