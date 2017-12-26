package wordsearch_tfx.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.input.KeyCombination
import todomvcfx.tornadofx.views.WordListView
import wordsearch_tfx.app.Styles
import tornadofx.*
import tornadofx.WizardStyles.Companion.graphic
import wordsearch_tfx.controller.WordSearchController
import wordsearch_tfx.model.WordGridModel

class WordSearchMainView : View("Word Search Puzzle Builder in TornadoFX") {
    private val wgModel: WordGridModel by inject()
    private val wsController: WordSearchController by inject()

    override val root = borderpane {
        setPrefSize(750.0, 450.0 )

        top = vbox {
            menubar {
                menu("Grid") {
                    checkmenuitem("Show Fill Letters",
                            KeyCombination.keyCombination("Ctrl+F"))
                            .selectedProperty().bindBidirectional(wgModel.fillLettersOnGrid)
                    separator()
                    item("Exit").action {
                        System.exit(0)
                    }
                }
                menu("Help") {
                    item("About Word Search Puzzle Builder...")
                            .action(wsController::helpAbout)
                }
            }
        }

        center = borderpane {
            center (WordGridView::class)
                    .addClass(Styles.wordGridView)

            right (WordListView::class)
        }
    }
}