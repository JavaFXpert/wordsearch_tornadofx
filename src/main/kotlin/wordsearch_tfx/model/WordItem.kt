@file:Suppress("unused")

package wordsearch_tfx.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import wordsearch_tfx.controller.WordSearchController
import java.util.*
import wordsearch_tfx.model.WordOrientation.*

class WordItem(text: String) {
    private val wgModel = find(WordGridModel::class)
    private val wsController = find(WordSearchController::class)
    val id = UUID.randomUUID()

    val textProperty = SimpleStringProperty(text)
    var text by textProperty

    val placedProperty = SimpleBooleanProperty()
    var placed by placedProperty

    var gridRow = 0
    var gridCol = 0;
    var wordOrientation: WordOrientation = HORIZ

    init {
        //TODO: Ascertain more Kotlin/TornadoFX-ish way to accomplish this
        placedProperty.addListener{
            e -> run {
                if (e is BooleanProperty && e.getValue()) {
                    // Place word randomly on grid
                    if (!wgModel.placeWord(this@WordItem)) {
                        wsController.showWordNotPlacedMessage(this@WordItem)
                        this@WordItem.placed = false
                        //TODO: Make the listview reflect the checked state of the
                        //      unplaced word (it currently remains checked)
                    }
                }
                else {
                    // Remove word from grid
                    wgModel.unplaceWord(this@WordItem)
                }
            }
        }

    }

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