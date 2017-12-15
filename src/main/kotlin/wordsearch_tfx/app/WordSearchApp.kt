package wordsearch_tfx.app

import javafx.application.Application
import wordsearch_tfx.view.MainView
import tornadofx.App

class WordSearchApp: App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    Application.launch(WordSearchApp::class.java, *args)
}