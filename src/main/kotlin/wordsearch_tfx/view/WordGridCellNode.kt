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

import javafx.scene.Cursor
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridCell

class WordGridCellNode(widthHeight: Double, wgCell: WordGridCell): Pane() {
    var rect = Rectangle()

    init {
        replaceChildren {
            rect = rectangle(-7.0, -22.0, widthHeight, widthHeight) {
                strokeWidth = 1.0
                stroke = Color.BLACK
                fill = Color.WHITE
            }
            text(" ").addClass(Styles.gridCellLetter).bind(wgCell.cellLetter)
        }

        wgCell.appearance.onChange {
            if (it is CellAppearance && it == CellAppearance.SELECTED_LOOK) {
                rect.strokeWidth = 2.0
                rect.stroke = Color.BLACK
                rect.fill = Color.LIGHTYELLOW
                cursor = Cursor.DEFAULT
            }
            else if (it is CellAppearance && it == CellAppearance.SELECTED_FIRST_LETTER_LOOK) {
                rect.strokeWidth = 2.0
                rect.stroke = Color.BLACK
                rect.fill = Color.LIGHTYELLOW
                cursor = Cursor.DEFAULT
            }
            else if (it is CellAppearance && it == CellAppearance.DRAGGING_LOOK) {
                rect.strokeWidth = 2.0
                rect.stroke = Color.BLACK
                rect.fill = Color.LIGHTYELLOW
                cursor = Cursor.DEFAULT
            }
            else if (it is CellAppearance && it == CellAppearance.CANT_DROP_LOOK) {
                rect.strokeWidth = 2.0
                rect.stroke = Color.BLACK
                rect.fill = Color.RED
                cursor = Cursor.DEFAULT
            }
            else if (it is CellAppearance && it == CellAppearance.DEFAULT_FIRST_LETTER_LOOK) {
                rect.strokeWidth = 1.0
                rect.stroke = Color.BLACK
                rect.fill = Color.WHITE
                cursor = Cursor.DEFAULT
            }
            else if (it is CellAppearance && it == CellAppearance.DEFAULT_LOOK) {
                rect.strokeWidth = 1.0
                rect.stroke = Color.BLACK
                rect.fill = Color.WHITE
                cursor = Cursor.DEFAULT
            }
        }
    }
}