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
package wordsearch_tfx.controller

import wordsearch_tfx.model.FilterState
import wordsearch_tfx.model.FilterState.Unplaced
import wordsearch_tfx.model.FilterState.Placed
import wordsearch_tfx.model.WordItem
import tornadofx.*

class WordStore : Controller() {
    val words = SortedFilteredList<WordItem>()

    fun addWord(text: String) = words.add(WordItem(text))

    fun removeWord(word: WordItem) = words.remove(word)

    fun togglePlaced(placed: Boolean) = with(words) {
        filter { it.placed != placed }.forEach { it.placed = placed }
        invalidate()
    }

    fun filterBy(state: FilterState) = when (state) {
        Placed -> words.predicate = { it.placed }
        Unplaced -> words.predicate = { !it.placed }
        else -> words.predicate = { true }
    }
}