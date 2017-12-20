package wordsearch_tfx.view

import wordsearch_tfx.app.Styles
import wordsearch_tfx.controller.WordStore
import tornadofx.*

class WordListHeader : View() {
    val store: WordStore by inject()
    val allDone = booleanBinding(store.words.items) { all { it.placed } }

    override val root = vbox {
        addClass(Styles.header)
        //label("words").setId(Styles.title)
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
                    store.addWord(text)
                    clear()
                }
            }
        }
    }
}