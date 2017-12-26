/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        placedProperty.addListener{
            e -> run {
                if (e is BooleanProperty && e.getValue()) {
                    // Place word randomly on grid
                    if (!wgModel.placeWord(this@WordItem)) {
                        wsController.showWordNotPlacedMessage(this@WordItem)
                        this@WordItem.placed = false
                    }
                    else {}
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