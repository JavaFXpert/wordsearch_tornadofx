package wordsearch_tfx.model

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

class WordGridModel(): ViewModel() {
    val rows: Int = 9
    val cols: Int = 9
    val fillLettersOnGrid: SimpleBooleanProperty = SimpleBooleanProperty(false)



}