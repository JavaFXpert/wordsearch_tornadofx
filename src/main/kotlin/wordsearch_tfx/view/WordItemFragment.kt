package wordsearch_tfx.view

import javafx.scene.layout.Priority
import wordsearch_tfx.app.Styles
import wordsearch_tfx.controller.WordStore
import wordsearch_tfx.model.WordItem
import wordsearch_tfx.model.WordItemModel
import tornadofx.*
import wordsearch_tfx.model.WordGridModel

class WordItemFragment : ListCellFragment<WordItem>() {
    private val wgModel: WordGridModel by inject()
    private val store: WordStore by inject()
    val word = WordItemModel(itemProperty)

    override val root = hbox {
        addClass(Styles.itemRoot)
        checkbox(property = word.placed) {
            action {
                startEdit()
                commitEdit(item)
            }
        }
        label(word.text) {
            setId(Styles.contentLabel)
            hgrow = Priority.ALWAYS
            useMaxSize = true
            removeWhen { editingProperty }
            toggleClass(Styles.strikethrough, word.placed)
        }
        textfield(word.text) {
            hgrow = Priority.ALWAYS
            removeWhen { editingProperty.not() }
            whenVisible { requestFocus() }
            action {
                item.text = item.text.toUpperCase()
                commitEdit(item)
            }
        }
        button(graphic = Styles.closeIcon()) {
            removeWhen { parent.hoverProperty().not().or(editingProperty) }
            action {
                if (item.placed) {
                    // Remove word from grid
                    wgModel.unplaceWord(item)
                    store.removeWord(item)
                }
            }
        }
    }

}