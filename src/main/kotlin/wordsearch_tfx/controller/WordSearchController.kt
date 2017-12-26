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
package wordsearch_tfx.controller

import tornadofx.*
import wordsearch_tfx.model.WordItem

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
    fun showWordNotPlacedMessage(wordItem: WordItem) {
        println("In wordNotPlaced");
        error("Word not placed",
                "The word: ${wordItem.text} was not successfully placed")
    }
}