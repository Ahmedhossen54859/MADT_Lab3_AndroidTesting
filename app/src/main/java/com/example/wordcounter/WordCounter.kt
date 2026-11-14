package com.example.wordcounter

/**
 * Data class holding the calculated text statistics.
 */
data class TextStatistics(
    val words: Int,
    val characters: Int,
    val charactersWithoutSpaces: Int,
    val sentences: Int,
    val paragraphs: Int
)

/**
 * WordCounter class containing all text calculation and analysis logic.
 * Designed to be clean, modular, and easily unit-testable without Android framework dependencies.
 */
class WordCounter {

    /**
     * Counts the total number of words in the given text.
     * Handles empty input, whitespace-only input, and multiple consecutive spaces/tabs/newlines.
     */
    fun countWords(text: String): Int {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) {
            return 0
        }
        // Split by one or more whitespace characters
        return trimmed.split(Regex("\\s+")).size
    }

    /**
     * Counts the total number of characters in the given text, including whitespace and newlines.
     */
    fun countCharacters(text: String): Int {
        return text.length
    }

    /**
     * Counts the total number of characters excluding all whitespace characters (spaces, tabs, newlines).
     */
    fun countCharactersWithoutSpaces(text: String): Int {
        return text.filter { !it.isWhitespace() }.length
    }

    /**
     * Counts the total number of sentences in the given text.
     * Sentences are separated by sentence-ending punctuation marks (. ! ?).
     * Handles empty input, multiple punctuation marks, and texts without ending punctuation.
     */
    fun countSentences(text: String): Int {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) {
            return 0
        }
        val sentences = trimmed.split(Regex("[.!?]+"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        return sentences.size
    }

    /**
     * Counts the total number of paragraphs in the given text.
     * Paragraphs are delimited by one or more newline characters.
     * Handles empty input, blank lines, and trailing newlines.
     */
    fun countParagraphs(text: String): Int {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) {
            return 0
        }
        val paragraphs = trimmed.split(Regex("(\\r?\\n)+"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        return paragraphs.size
    }

    /**
     * Calculates all statistics for the given text.
     */
    fun calculate(text: String): TextStatistics {
        return TextStatistics(
            words = countWords(text),
            characters = countCharacters(text),
            charactersWithoutSpaces = countCharactersWithoutSpaces(text),
            sentences = countSentences(text),
            paragraphs = countParagraphs(text)
        )
    }
}
