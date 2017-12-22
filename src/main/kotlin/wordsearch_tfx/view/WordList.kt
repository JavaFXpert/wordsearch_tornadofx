package wordsearch_tfx.view

import wordsearch_tfx.controller.WordStore
import tornadofx.*
import wordsearch_tfx.model.WordGridModel

class WordList : View() {
    private val wgModel: WordGridModel by inject()
    private val store: WordStore by inject()

    override val root = listview(store.words) {
        isEditable = true
        cellFragment(WordItemFragment::class)
        disableWhen(wgModel.fillLettersOnGrid)
    }
}