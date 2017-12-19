@file:Suppress("unused")

package wordsearch_tfx.model

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.*

class WordItem(text: String) {
    val id = UUID.randomUUID()

    val textProperty = SimpleStringProperty(text)
    var text by textProperty

    val placedProperty = SimpleBooleanProperty()
    var placed by placedProperty

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as WordItem

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

class WordItemModel(property: ObjectProperty<WordItem>) :
        ItemViewModel<WordItem>(itemProperty = property) {
    val text = bind(autocommit = true) {
        item?.textProperty }
    val placed = bind(autocommit = true) { item?.placedProperty }
}

enum class FilterState { All, Unplaced, Placed }