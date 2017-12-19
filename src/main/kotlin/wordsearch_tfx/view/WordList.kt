package wordsearch_tfx.view

import wordsearch_tfx.controller.WordStore
import tornadofx.*

class WordList : View() {
    val store: WordStore by inject()

    override val root = listview(store.words) {
        isEditable = true
        cellFragment(WordItemFragment::class)
    }
}