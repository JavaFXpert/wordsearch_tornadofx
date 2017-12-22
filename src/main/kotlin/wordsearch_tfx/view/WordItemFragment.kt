package wordsearch_tfx.view

import javafx.scene.layout.Priority
import wordsearch_tfx.app.Styles
import wordsearch_tfx.controller.WordStore
import wordsearch_tfx.model.WordItem
import wordsearch_tfx.model.WordItemModel
import tornadofx.*

class WordItemFragment : ListCellFragment<WordItem>() {
    val store: WordStore by inject()
    val word = WordItemModel(itemProperty)

    override val root = hbox {
        // Enable if you want ellipsis instead of text overflow
//        cellProperty.onChange { cell ->
//            if (cell != null)
//                maxWidthProperty().cleanBind(cell.widthProperty().minus(15))
//        }
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
            action { store.removeWord(item) }
        }
    }

}