package wordsearch_tfx.view

import javafx.scene.input.KeyCombination
import wordsearch_tfx.app.Styles
import tornadofx.*
import tornadofx.WizardStyles.Companion.graphic

class MainView : View("Word Search Puzzle Builder in TornadoFX") {
    override val root = borderpane() {
        top = vbox {
            menubar {
                menu("Grid") {
                    item("Place Word...",
                            "Ctrl+P",
                            imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/place_word.gif?raw=true"))
                    //TODO: How best to use local image resources?
                    item("Place Word Randomly...",
                            "Ctrl+R",
                            imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/place_random.gif?raw=true"))
                    item("Place All Words Randomly...", "Alt+P")
                    separator()
                    item("Unplace Word...",
                            "Ctrl+U",
                            imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/unplace_word.gif?raw=true"))
                    item("Unplace All Words...", "Alt+U")
                    checkmenuitem("Show Fill Letters", KeyCombination.keyCombination("Ctrl+F"))
                    separator()
                    item("Exit")
                }
                menu("WordList") {
                    item("Add Word",
                            "Shortcut+A",
                            imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/add_word.gif?raw=true"))
                    item("Delete Word",
                            "Shortcut+D")
                }
                menu("Help") {
                    item("About Word Search Puzzle Builder...")
                }
            }
            //button("This will be a toolbar")
            toolbar {
                button {
                    graphic = imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/place_word.gif?raw=true")
                    tooltip("Place word on grid")
                }
                button {
                    graphic = imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/place_random.gif?raw=true")
                    tooltip("Place word randomly on grid")
                }
                button {
                    graphic = imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/unplace_word.gif?raw=true")
                    tooltip("Unplace (remove) word from grid")
                }
                button {
                    graphic = imageview("https://github.com/JavaFXpert/wordsearch_jfx/blob/master/images/add_word.gif?raw=true")
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