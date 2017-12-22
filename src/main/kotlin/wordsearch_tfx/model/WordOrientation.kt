package wordsearch_tfx.model

enum class WordOrientation(val id: Int) {
    HORIZ(0),
    DIAG_DOWN(1),
    VERT(2),
    DIAG_UP(3)
}