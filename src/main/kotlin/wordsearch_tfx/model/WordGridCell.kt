package wordsearch_tfx.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class WordGridCell() {
    var cellLetter = SimpleStringProperty(" ")
    val fillLetter =
            SimpleStringProperty(Character.forDigit(
                    (Math.floor(Math.random() * 26 + 10)).toInt(), 36).toString().toUpperCase())

}