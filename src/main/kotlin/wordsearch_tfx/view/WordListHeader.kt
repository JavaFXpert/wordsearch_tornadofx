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