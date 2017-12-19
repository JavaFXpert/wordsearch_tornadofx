package wordsearch_tfx.controller

import tornadofx.*

class WordSearchController: Controller() {
    fun gridPlaceWord() {
        println("In gridPlaceWord");
    }
    fun gridPlaceWordRandomly() {
        println("In gridPlaceWordRandomly");
    }
    fun gridPlaceAllWords() {
        println("In gridPlaceAllWords");
    }
    fun gridUnplaceWord() {
        println("In gridUnplaceWord");
    }
    fun gridUnplaceAllWords() {
        println("In gridUnplaceAllWords");
    }
    fun wordListAddWord() {
        println("In wordListAddWord");
    }
    fun wordListDeleteWord() {
        println("In wordListDeleteWord");
    }
    fun helpAbout() {
        println("In helpAbout");
        information("About Word Search Puzzle Builder",
                "A TornadoFX example program by James L. Weaver")
    }
}