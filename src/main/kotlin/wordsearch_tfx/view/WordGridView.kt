package wordsearch_tfx.view

import tornadofx.*
import wordsearch_tfx.model.WordGridModel

class WordGridView : View() {
    private val wgModel: WordGridModel by inject()
    override val root = pane {

    }
}
