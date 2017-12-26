/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wordsearch_tfx.view

import javafx.scene.input.KeyCombination
import todomvcfx.tornadofx.views.WordListView
import wordsearch_tfx.app.Styles
import tornadofx.*
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