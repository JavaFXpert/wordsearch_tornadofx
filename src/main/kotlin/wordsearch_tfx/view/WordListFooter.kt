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
