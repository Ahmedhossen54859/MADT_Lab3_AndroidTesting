package com.example.wordcounter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WordCounterTest {

    private lateinit var wordCounter: WordCounter

    @Before
    fun setUp() {
        wordCounter = WordCounter()
    }

    // ==========================================
    // Tests for countWords
    // ==========================================

    @Test
    fun countWords_emptyString_returnsZero() {
        assertEquals(0, wordCounter.countWords(""))
    }

    @Test
    fun countWords_whitespaceOnly_returnsZero() {
        assertEquals(0, wordCounter.countWords("   "))
        assertEquals(0, wordCounter.countWords("\t\t"))
        assertEquals(0, wordCounter.countWords("\n\r\n"))
        assertEquals(0, wordCounter.countWords("   \t  \n  \r\n  "))
    }

    @Test
    fun countWords_singleWord_returnsOne() {
        assertEquals(1, wordCounter.countWords("Hello"))
        assertEquals(1, wordCounter.countWords(" Kotlin "))
        assertEquals(1, wordCounter.countWords("\tAndroid\n"))
    }

    @Test
    fun countWords_multipleWordsSingleSpace_returnsCorrectCount() {
        assertEquals(2, wordCounter.countWords("Hello World"))
        assertEquals(3, wordCounter.countWords("One Two Three"))
        assertEquals(5, wordCounter.countWords("The quick brown fox jumps"))
    }

    @Test
    fun countWords_multipleWordsConsecutiveWhitespace_returnsCorrectCount() {
        assertEquals(2, wordCounter.countWords("Hello    World"))
        assertEquals(3, wordCounter.countWords("One\t\tTwo\n\nThree"))
        assertEquals(4, wordCounter.countWords("   Leading   middle\t\tand   trailing   "))
    }

    @Test
    fun countWords_specialCharactersAndNumbers_returnsCorrectCount() {
        assertEquals(3, wordCounter.countWords("Unit 100 test"))
        assertEquals(2, wordCounter.countWords("don't well-known"))
        assertEquals(4, wordCounter.countWords("foo-bar baz & qux"))
    }

    // ==========================================
    // Tests for countCharacters
    // ==========================================

    @Test
    fun countCharacters_emptyString_returnsZero() {
        assertEquals(0, wordCounter.countCharacters(""))
    }

    @Test
    fun countCharacters_whitespaceOnly_returnsLength() {
        assertEquals(3, wordCounter.countCharacters("   "))
        assertEquals(2, wordCounter.countCharacters("\t\n"))
    }

    @Test
    fun countCharacters_textWithSpacesAndNewlines_returnsTotalLength() {
        val text = "Hello World!\nLine 2."
        assertEquals(text.length, wordCounter.countCharacters(text))
    }

    @Test
    fun countCharacters_unicodeAndSymbols_returnsLength() {
        val text = "Kotlin 🚀 @#$"
        assertEquals(text.length, wordCounter.countCharacters(text))
    }

    // ==========================================
    // Tests for countCharactersWithoutSpaces
    // ==========================================

    @Test
    fun countCharactersWithoutSpaces_emptyString_returnsZero() {
        assertEquals(0, wordCounter.countCharactersWithoutSpaces(""))
    }

    @Test
    fun countCharactersWithoutSpaces_whitespaceOnly_returnsZero() {
        assertEquals(0, wordCounter.countCharactersWithoutSpaces(" \t\n\r "))
    }

    @Test
    fun countCharactersWithoutSpaces_textWithSpaces_returnsNonWhitespaceCount() {
        assertEquals(10, wordCounter.countCharactersWithoutSpaces("Hello World"))
        assertEquals(3, wordCounter.countCharactersWithoutSpaces(" a   b   c "))
        assertEquals(4, wordCounter.countCharactersWithoutSpaces("1\t2\n3\r4"))
    }

    @Test
    fun countCharactersWithoutSpaces_noWhitespace_returnsFullLength() {
        val text = "Supercalifragilisticexpialidocious"
        assertEquals(text.length, wordCounter.countCharactersWithoutSpaces(text))
    }

    // ==========================================
    // Tests for countSentences
    // ==========================================

    @Test
    fun countSentences_emptyString_returnsZero() {
        assertEquals(0, wordCounter.countSentences(""))
    }

    @Test
    fun countSentences_whitespaceOnly_returnsZero() {
        assertEquals(0, wordCounter.countSentences("   \t  \n  "))
    }

    @Test
    fun countSentences_punctuationOnly_returnsZero() {
        assertEquals(0, wordCounter.countSentences("."))
        assertEquals(0, wordCounter.countSentences("..."))
        assertEquals(0, wordCounter.countSentences("! ? . !?"))
        assertEquals(0, wordCounter.countSentences("   ...   ??? !!!   "))
    }

    @Test
    fun countSentences_singleSentenceWithoutPunctuation_returnsOne() {
        assertEquals(1, wordCounter.countSentences("Hello world"))
        assertEquals(1, wordCounter.countSentences("   Just one sentence without end punctuation   "))
    }

    @Test
    fun countSentences_singleSentenceWithVariousPunctuation_returnsOne() {
        assertEquals(1, wordCounter.countSentences("Hello world."))
        assertEquals(1, wordCounter.countSentences("Hello world!"))
        assertEquals(1, wordCounter.countSentences("Hello world?"))
        assertEquals(1, wordCounter.countSentences("Hello world..."))
        assertEquals(1, wordCounter.countSentences("What a day?!"))
    }

    @Test
    fun countSentences_multipleSentences_returnsCorrectCount() {
        val text = "First sentence. Second sentence! Third sentence? Fourth sentence without period"
        assertEquals(4, wordCounter.countSentences(text))
    }

    @Test
    fun countSentences_multipleConsecutivePunctuation_returnsCorrectCount() {
        val text = "Wait... Are you sure?! Yes, absolutely!!"
        assertEquals(3, wordCounter.countSentences(text))
    }

    @Test
    fun countSentences_multilineText_returnsCorrectCount() {
        val text = "Line 1 sentence.\nLine 2 sentence!\nLine 3 sentence?"
        assertEquals(3, wordCounter.countSentences(text))
    }

    @Test
    fun countSentences_withLeadingAndTrailingPunctuation_returnsCorrectCount() {
        val text = "...Beginning sentence. Ending sentence..."
        assertEquals(2, wordCounter.countSentences(text))
    }

    // ==========================================
    // Tests for countParagraphs
    // ==========================================

    @Test
    fun countParagraphs_emptyString_returnsZero() {
        assertEquals(0, wordCounter.countParagraphs(""))
    }

    @Test
    fun countParagraphs_whitespaceOnly_returnsZero() {
        assertEquals(0, wordCounter.countParagraphs("   \n\n\t\n  "))
    }

    @Test
    fun countParagraphs_singleLine_returnsOne() {
        assertEquals(1, wordCounter.countParagraphs("Single paragraph line."))
        assertEquals(1, wordCounter.countParagraphs("   Trimmed single paragraph.   "))
    }

    @Test
    fun countParagraphs_multipleParagraphsUnixNewlines_returnsCorrectCount() {
        val text = "Paragraph 1\nParagraph 2\nParagraph 3"
        assertEquals(3, wordCounter.countParagraphs(text))
    }

    @Test
    fun countParagraphs_multipleParagraphsWindowsNewlines_returnsCorrectCount() {
        val text = "Paragraph 1\r\nParagraph 2\r\nParagraph 3"
        assertEquals(3, wordCounter.countParagraphs(text))
    }

    @Test
    fun countParagraphs_multipleConsecutiveNewlinesAndBlankLines_returnsCorrectCount() {
        val text = "\n\nParagraph 1\n\n\n\nParagraph 2\r\n\r\nParagraph 3\n\n"
        assertEquals(3, wordCounter.countParagraphs(text))
    }

    @Test
    fun countParagraphs_blankLinesContainingWhitespace_returnsCorrectCount() {
        val text = "Paragraph 1\n   \n\t\nParagraph 2"
        assertEquals(2, wordCounter.countParagraphs(text))
    }

    // ==========================================
    // Tests for calculate() and TextStatistics
    // ==========================================

    @Test
    fun calculate_emptyString_returnsAllZeros() {
        val stats = wordCounter.calculate("")
        assertEquals(0, stats.words)
        assertEquals(0, stats.characters)
        assertEquals(0, stats.charactersWithoutSpaces)
        assertEquals(0, stats.sentences)
        assertEquals(0, stats.paragraphs)
    }

    @Test
    fun calculate_whitespaceString_returnsCorrectStats() {
        val stats = wordCounter.calculate("   \n\t  ")
        assertEquals(0, stats.words)
        assertEquals(7, stats.characters)
        assertEquals(0, stats.charactersWithoutSpaces)
        assertEquals(0, stats.sentences)
        assertEquals(0, stats.paragraphs)
    }

    @Test
    fun calculate_normalText_returnsCorrectStats() {
        val text = "Hello world! This is Kotlin.\nSecond paragraph is here."
        val stats = wordCounter.calculate(text)

        assertEquals(9, stats.words)
        assertEquals(text.length, stats.characters)
        assertEquals(46, stats.charactersWithoutSpaces)
        assertEquals(3, stats.sentences)
        assertEquals(2, stats.paragraphs)
    }

    // ==========================================
    // Tests for TextStatistics data class coverage
    // ==========================================

    @Test
    fun textStatistics_dataClassMethods_behaveCorrectly() {
        val stats1 = TextStatistics(
            words = 10,
            characters = 50,
            charactersWithoutSpaces = 42,
            sentences = 3,
            paragraphs = 2
        )
        val stats2 = TextStatistics(
            words = 10,
            characters = 50,
            charactersWithoutSpaces = 42,
            sentences = 3,
            paragraphs = 2
        )
        val statsDifferent = TextStatistics(
            words = 5,
            characters = 20,
            charactersWithoutSpaces = 16,
            sentences = 1,
            paragraphs = 1
        )

        // Properties
        assertEquals(10, stats1.words)
        assertEquals(50, stats1.characters)
        assertEquals(42, stats1.charactersWithoutSpaces)
        assertEquals(3, stats1.sentences)
        assertEquals(2, stats1.paragraphs)

        // Equals & HashCode
        assertEquals(stats1, stats2)
        assertEquals(stats1.hashCode(), stats2.hashCode())
        assertNotEquals(stats1, statsDifferent)
        assertFalse(stats1.equals(null))
        assertFalse(stats1.equals("Some other type"))
        assertTrue(stats1.equals(stats1))

        // Check inequality on each distinct property
        assertNotEquals(stats1, stats1.copy(words = 99))
        assertNotEquals(stats1, stats1.copy(characters = 99))
        assertNotEquals(stats1, stats1.copy(charactersWithoutSpaces = 99))
        assertNotEquals(stats1, stats1.copy(sentences = 99))
        assertNotEquals(stats1, stats1.copy(paragraphs = 99))

        // Copy
        val copied = stats1.copy(words = 12)
        assertEquals(12, copied.words)
        assertEquals(50, copied.characters)

        // Destructuring (component1 .. component5)
        val (words, chars, charsNoSpaces, sentences, paras) = stats1
        assertEquals(10, words)
        assertEquals(50, chars)
        assertEquals(42, charsNoSpaces)
        assertEquals(3, sentences)
        assertEquals(2, paras)

        // ToString
        val str = stats1.toString()
        assertTrue(str.contains("words=10"))
        assertTrue(str.contains("characters=50"))
        assertTrue(str.contains("charactersWithoutSpaces=42"))
        assertTrue(str.contains("sentences=3"))
        assertTrue(str.contains("paragraphs=2"))
    }
}
