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
        //TODO: override start in the app class and configure the stage
        setPrefSize(750.0, 450.0 )

        top = vbox {
            menubar {
                menu("Grid") {
                    /*
                    item("Place All Words Randomly", "Ctrl+P") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsController::gridPlaceAllWords)
                    }
                    item("Unplace All Words", "Ctrl+U") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsController::gridUnplaceAllWords)
                    }
                    */
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
            /*
            toolbar {
                //TODO: Style buttons to remove borders
                button {
                    imageview("/place_random.gif")
                    tooltip("Place word randomly on grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsController::gridPlaceWordRandomly)
                    }
                }
                button {
                    imageview("/unplace_word.gif")
                    tooltip("Unplace (remove) word from grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsController::gridUnplaceWord)
                    }
                }
                //TODO: Implement a toggle button for show fill letters
                button {
                    imageview("/place_word.gif")
                    tooltip("Show Fill Letters") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsController::gridPlaceWord)
                    }
                }
            }
            */
        }

        center = borderpane {
            center (WordGridView::class)
                    .addClass(Styles.wordGridView)

            right (WordListView::class)
        }
    }
}