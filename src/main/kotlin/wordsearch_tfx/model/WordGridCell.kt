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