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