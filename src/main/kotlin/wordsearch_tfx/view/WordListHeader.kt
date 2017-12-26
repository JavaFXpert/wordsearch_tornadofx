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
package wordsearch_tfx.view

import wordsearch_tfx.app.Styles
import wordsearch_tfx.controller.WordStore
import tornadofx.*
import wordsearch_tfx.model.WordGridModel

class WordListHeader : View() {
    private val wgModel: WordGridModel by inject()
    private val store: WordStore by inject()
    val allDone = booleanBinding(store.words.items) { all { it.placed } }

    override val root = vbox {
        addClass(Styles.header)
        hbox {
            addClass(Styles.addItemRoot)
            checkbox {
                addClass(Styles.mainCheckBox)
                visibleWhen { booleanBinding(store.words) { isNotEmpty() } }
                action { store.togglePlaced(isSelected) }
                allDone.onChange { isSelected = it }
            }
            textfield {
                promptText = "Word to add"
                action {
                    store.addWord(text.toUpperCase())
                    clear()
                }
            }
        }
        disableWhen(wgModel.fillLettersOnGrid)
    }
}