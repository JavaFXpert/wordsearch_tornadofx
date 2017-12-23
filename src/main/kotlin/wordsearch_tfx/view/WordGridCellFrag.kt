package wordsearch_tfx.view

import javafx.animation.Interpolator
import javafx.geometry.Point2D
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import wordsearch_tfx.app.Styles
import wordsearch_tfx.model.WordGridCell
import wordsearch_tfx.model.WordGridModel

class WordGridCellFrag(row: Int, col: Int, widthHeight: Double, wgCell: WordGridCell): Fragment() {

    override val root = pane {
        //TODO: Ascertain best way to translate (without animation) the rectangle and text as a group.
        //      Potentially do this in WordGridView where instances of this class are created.
        //      Note that when a way is identified, the rectangle will have 0,0 pass in for x,y

        rectangle(col * widthHeight, row * widthHeight, widthHeight, widthHeight) {
            stroke = Color.BLACK
            fill = Color.TRANSPARENT
        }
        pane {
            text(" ").addClass(Styles.gridCellLetter).bind(wgCell.cellLetter)
            move(0.1.seconds, Point2D(col * widthHeight + 7, row * widthHeight + 22), Interpolator.LINEAR)
        }
    }
}