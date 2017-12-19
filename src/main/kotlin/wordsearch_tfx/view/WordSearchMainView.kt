package wordsearch_tfx.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.input.KeyCombination
import wordsearch_tfx.app.Styles
import tornadofx.*
import tornadofx.WizardStyles.Companion.graphic
import wordsearch_tfx.controller.WordSearchHandlers
import wordsearch_tfx.model.WordGridModel

class MainView : View("Word Search Puzzle Builder in TornadoFX") {
    private val wgModel: WordGridModel by inject()
    private val wsHandlers: WordSearchHandlers by inject()

    override val root = borderpane {
        //TODO: override start in the app class and configure the stage
        setPrefSize(750.0, 450.0 )

        top = vbox {
            menubar {
                menu("Grid") {
                    item("Place Word...",
                            "Ctrl+P",
                            imageview("/place_word.gif")) {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridPlaceWord)
                    }
                    item("Place Word Randomly...",
                            "Ctrl+R",
                            imageview("/place_random.gif")) {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridPlaceWordRandomly)
                    }
                    item("Place All Words Randomly...", "Alt+P") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridPlaceAllWords)
                    }
                    separator()
                    item("Unplace Word...",
                            "Ctrl+U",
                            imageview("/unplace_word.gif")) {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridUnplaceWord)
                    }
                    item("Unplace All Words...", "Alt+U") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridUnplaceAllWords)
                    }
                    checkmenuitem("Show Fill Letters",
                            KeyCombination.keyCombination("Ctrl+F"))
                            .selectedProperty().bindBidirectional(wgModel.fillLettersOnGrid)
                    separator()
                    item("Exit").action {
                        System.exit(0)
                    }
                }
                menu("WordList") {
                    item("Add Word",
                            "Ctrl+A",
                            imageview("/add_word.gif"))
                            .action(wsHandlers::wordListAddWord)
                    item("Delete Word",
                            "Ctrl+D") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::wordListDeleteWord)
                    }
                }
                menu("Help") {
                    item("About Word Search Puzzle Builder...")
                            .action(wsHandlers::helpAbout)
                }
            }
            toolbar {
                //TODO: Style buttons to remove borders
                button {
                    imageview("/place_word.gif")
                    tooltip("Place word on grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridPlaceWord)
                    }
                }
                button {
                    imageview("/place_random.gif")
                    tooltip("Place word randomly on grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridPlaceWordRandomly)
                    }
                }
                button {
                    imageview("/unplace_word.gif")
                    tooltip("Unplace (remove) word from grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                        action(wsHandlers::gridUnplaceWord)
                    }
                }
                button {
                    imageview("/add_word.gif")
                    tooltip("Add word to word list")
                    action(wsHandlers::wordListAddWord)
                }
            }
        }
    }
}