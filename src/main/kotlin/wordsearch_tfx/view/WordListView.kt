package todomvcfx.tornadofx.views

import tornadofx.*
import wordsearch_tfx.model.WordGridModel
import wordsearch_tfx.view.WordList
import wordsearch_tfx.view.WordListFooter
import wordsearch_tfx.view.WordListHeader

class WordListView : View() {
    override val root = borderpane {
        top(WordListHeader::class)
        center(WordList::class)
        bottom(WordListFooter::class)
    }
}