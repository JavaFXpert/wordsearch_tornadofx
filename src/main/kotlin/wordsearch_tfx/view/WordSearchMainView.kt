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
                            "Shortcut+A",
                            imageview("/add_word.gif"))
                    item("Delete Word",
                            "Shortcut+D")
                            .disableWhen(wgModel.fillLettersOnGrid)
                }
                menu("Help") {
                    item("About Word Search Puzzle Builder...")
                }
            }
            toolbar {
                //TODO: Style buttons to remove borders
                button {
                    imageview("/place_word.gif")
                    tooltip("Place word on grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                    }
                }
                button {
                    imageview("/place_random.gif")
                    tooltip("Place word randomly on grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                    }
                }
                button {
                    imageview("/unplace_word.gif")
                    tooltip("Unplace (remove) word from grid") {
                        disableWhen(wgModel.fillLettersOnGrid)
                    }
                }
                button {
                    imageview("/add_word.gif")
                    tooltip("Add word to word list")
                }
            }

            /*
            menus: [
              Menu {
                text: "Grid"
                mnemonic: G
                items: [
                  MenuItem {
                    text: "Place Word..."
                    mnemonic: P
                    accelerator: {
                      modifier: CTRL
                      keyStroke: P
                    }
                    enabled: bind not wgModel.fillLettersOnGrid
                    action: operation() {
                      wsHandlers.gridPlaceWord();
                    }
                  },
                  MenuItem {
                    text: "Place Word Randomly..."
                    mnemonic: R
                    accelerator: {
                      modifier: CTRL
                      keyStroke: R
                    }
                    enabled: bind not wgModel.fillLettersOnGrid
                    action: operation() {
                      wsHandlers.gridPlaceWordRandomly();
                    }
                  },
                  MenuItem {
                    text: "Place All Words Randomly..."
                    mnemonic: A
                    accelerator: {
                      modifier: ALT
                      keyStroke: P
                    }
                    enabled: bind not wgModel.fillLettersOnGrid
                    action: operation() {
                      wsHandlers.gridPlaceAllWords();
                    }
                  },
                  MenuSeparator,
                  MenuItem {
                    text: "Unplace Word..."
                    mnemonic: U
                    accelerator: {
                      modifier: CTRL
                      keyStroke: U
                    }
                    enabled: bind not wgModel.fillLettersOnGrid
                    action: operation() {
                      wsHandlers.gridUnplaceWord();
                    }
                  },
                  MenuItem {
                    text: "Unplace All Words..."
                    mnemonic: L
                    accelerator: {
                      modifier: ALT
                      keyStroke: U
                    }
                    enabled: bind not wgModel.fillLettersOnGrid
                    action: operation() {
                      wsHandlers.gridUnplaceAllWords();
                    }
                  },
                  CheckBoxMenuItem {
                    text: "Show Fill Letters"
                    selected: bind wgModel.fillLettersOnGrid
                    mnemonic: F
                    accelerator: {
                      modifier: CTRL
                      keyStroke: F
                    }
                  },
                  MenuSeparator,
                  MenuItem {
                    text: "Exit"
                    mnemonic: X
                    action: operation() {
                      System.exit(0);
                    }
                  },
                ]
              },
              Menu {
                text: "WordList"
                mnemonic: W
                items: [
                  MenuItem {
                    text: "Add Word"
                    mnemonic: W
                    accelerator: {
                      keyStroke: INSERT
                    }
                    action: operation() {
                      wsHandlers.wordListAddWord();
                    }
                  },
                  MenuItem {
                    text: "Delete Word"
                    mnemonic: D
                    accelerator: {
                      keyStroke: DELETE
                    }
                    enabled: bind not wgModel.fillLettersOnGrid
                    action: operation() {
                      wsHandlers.wordListDeleteWord();
                    }
                  }
                ]
              },
              Menu {
                text: "Help"
                mnemonic: H
                items: [
                  MenuItem {
                    text: "About Word Search Puzzle Builder..."
                    mnemonic: A
                    action: operation() {
                      MessageDialog {
                        title: "About Word Search Puzzle Builder"
                        message: "A JavaFX Script example program by James L. Weaver
        (jim.weaver at jmentor dot com).  Last revised July 2007."
                        messageType: INFORMATION
                        visible: true
                      }
                    }
                  }
                ]
              }
            ]

             */
        }
    }
}