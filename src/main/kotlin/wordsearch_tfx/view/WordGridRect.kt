package wordsearch_tfx.view

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import wordsearch_tfx.model.WordGridModel

class WordGridRect(row: Int, col: Int, widthHeight: Double):
        Rectangle (col * widthHeight, row * widthHeight, widthHeight, widthHeight) {
    private val wgModel: WordGridModel = find(WordGridModel::class)

    init {
        stroke = Color.BLACK
        fill = Color.TRANSPARENT
    }
}