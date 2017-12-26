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
import wordsearch_tfx.model.FilterState
import tornadofx.*

class WordListFooter : View() {
    val store: WordStore by inject()
    val itemsUnplaced = integerBinding(store.words.items) { count { !it.placed } }

    override val root = hbox {
        addClass(Styles.footer)
        label(stringBinding(itemsUnplaced) { "$value word${value.plural} unplaced" })
        hbox {
            togglegroup {
                for (state in FilterState.values())
                    togglebutton(state.name).whenSelected { store.filterBy(state) }
            }
        }
    }

    val Int.plural: String get() = if (this == 1) "" else "s"
}
