package wordsearch_tfx.model

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

class WordGridModel: ViewModel() {
    val fillLettersOnGrid: SimpleBooleanProperty = SimpleBooleanProperty(false)
}