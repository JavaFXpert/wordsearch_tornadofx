package wordsearch_tfx.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import wordsearch_tfx.view.CellAppearance
import wordsearch_tfx.view.CellAppearance.*

class WordGridCell() {
    var cellLetter = SimpleStringProperty(" ")
    val fillLetter =
            SimpleStringProperty(Character.forDigit(
                    (Math.floor(Math.random() * 26 + 10)).toInt(), 36).toString().toUpperCase())

    val appearance = SimpleObjectProperty<CellAppearance>(DEFAULT_LOOK)

    // Word grid entries associated with this cell on the grid
    val wordItems: ArrayList<WordItem> = ArrayList()

}