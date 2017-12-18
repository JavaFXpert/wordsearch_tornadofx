package wordsearch_tfx.app

import javafx.application.Application
import tornadofx.*
import wordsearch_tfx.view.MainView

class WordSearchApp: App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    launch<WordSearchApp>(args)
}
