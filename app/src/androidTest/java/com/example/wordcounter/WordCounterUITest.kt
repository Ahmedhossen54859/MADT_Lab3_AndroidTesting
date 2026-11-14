package com.example.wordcounter

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordCounterUITest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    /**
     * Scenario 1: Enter normal text and verify all calculated results.
     */
    @Test
    fun testScenario1_enterNormalText_verifiesAllCalculatedResults() {
        val normalText = "The quick brown fox jumps over the lazy dog. It was a sunny day."
        // 14 words, 64 characters, 53 characters without spaces, 2 sentences, 1 paragraph

        onView(withId(R.id.etInputText))
            .perform(clearText(), typeText(normalText), closeSoftKeyboard())

        onView(withId(R.id.btnCalculate)).perform(click())

        onView(withId(R.id.tvTotalWords)).check(matches(withText("14")))
        onView(withId(R.id.tvTotalCharacters)).check(matches(withText("64")))
        onView(withId(R.id.tvCharsNoSpaces)).check(matches(withText("51")))
        onView(withId(R.id.tvTotalSentences)).check(matches(withText("2")))
        onView(withId(R.id.tvTotalParagraphs)).check(matches(withText("1")))
    }

    /**
     * Scenario 2: Enter text with multiple spaces and verify word counting.
     */
    @Test
    fun testScenario2_enterTextWithMultipleSpaces_verifiesWordCounting() {
        val textWithSpaces = "   Android    Studio   Espresso   "
        // 3 words, 34 characters, 21 characters without spaces, 1 sentence, 1 paragraph

        onView(withId(R.id.etInputText))
            .perform(clearText(), typeText(textWithSpaces), closeSoftKeyboard())

        onView(withId(R.id.btnCalculate)).perform(click())

        onView(withId(R.id.tvTotalWords)).check(matches(withText("3")))
        onView(withId(R.id.tvTotalCharacters)).check(matches(withText("34")))
        onView(withId(R.id.tvCharsNoSpaces)).check(matches(withText("21")))
        onView(withId(R.id.tvTotalSentences)).check(matches(withText("1")))
        onView(withId(R.id.tvTotalParagraphs)).check(matches(withText("1")))
    }

    /**
     * Scenario 3: Click Calculate with empty input and verify the warning.
     */
    @Test
    fun testScenario3_clickCalculateWithEmptyInput_verifiesWarningAndReset() {
        // First enter some text and calculate to populate non-zero statistics
        onView(withId(R.id.etInputText))
            .perform(clearText(), typeText("Initial test text"), closeSoftKeyboard())
        onView(withId(R.id.btnCalculate)).perform(click())
        onView(withId(R.id.tvTotalWords)).check(matches(withText("3")))

        // Now clear the input field completely
        onView(withId(R.id.etInputText)).perform(clearText(), closeSoftKeyboard())

        // Click Calculate with empty input
        onView(withId(R.id.btnCalculate)).perform(click())

        // Verify the warning message displayed on the input field
        onView(withId(R.id.etInputText))
            .check(matches(hasErrorText("Please enter some text to calculate.")))

        // Verify all statistics counters are reset to "0"
        onView(withId(R.id.tvTotalWords)).check(matches(withText("0")))
        onView(withId(R.id.tvTotalCharacters)).check(matches(withText("0")))
        onView(withId(R.id.tvCharsNoSpaces)).check(matches(withText("0")))
        onView(withId(R.id.tvTotalSentences)).check(matches(withText("0")))
        onView(withId(R.id.tvTotalParagraphs)).check(matches(withText("0")))
    }

    /**
     * Scenario 4: Click Clear and verify that input and results are reset.
     */
    @Test
    fun testScenario4_clickClear_verifiesInputAndResultsReset() {
        // First enter some text and calculate
        onView(withId(R.id.etInputText))
            .perform(clearText(), typeText("Sample test text for clear."), closeSoftKeyboard())
        onView(withId(R.id.btnCalculate)).perform(click())

        // Verify results were calculated
        onView(withId(R.id.tvTotalWords)).check(matches(withText("5")))

        // Now click the Clear button
        onView(withId(R.id.btnClear)).perform(click())

        // Verify input text is cleared
        onView(withId(R.id.etInputText)).check(matches(withText("")))

        // Verify all statistics counters are reset to "0"
        onView(withId(R.id.tvTotalWords)).check(matches(withText("0")))
        onView(withId(R.id.tvTotalCharacters)).check(matches(withText("0")))
        onView(withId(R.id.tvCharsNoSpaces)).check(matches(withText("0")))
        onView(withId(R.id.tvTotalSentences)).check(matches(withText("0")))
        onView(withId(R.id.tvTotalParagraphs)).check(matches(withText("0")))
    }

    /**
     * Scenario 5: Enter multiline text with multiple sentences and paragraphs.
     */
    @Test
    fun testScenario5_enterMultilineText_verifiesSentencesAndParagraphs() {
        val multiLineText = "First paragraph sentence one. Sentence two!\n\nSecond paragraph sentence three?"
        // Words: "First"(1) "paragraph"(2) "sentence"(3) "one."(4) "Sentence"(5) "two!"(6) "Second"(7) "paragraph"(8) "sentence"(9) "three?"(10) -> 10 words
        // Chars: 43 + 2 + 32 = 77
        // Chars without spaces: 36 + 28 = 64
        // Sentences: 3
        // Paragraphs: 2

        onView(withId(R.id.etInputText))
            .perform(clearText(), typeText(multiLineText), closeSoftKeyboard())

        onView(withId(R.id.btnCalculate)).perform(click())

        onView(withId(R.id.tvTotalWords)).check(matches(withText("10")))
        onView(withId(R.id.tvTotalCharacters)).check(matches(withText("77")))
        onView(withId(R.id.tvCharsNoSpaces)).check(matches(withText("67")))
        onView(withId(R.id.tvTotalSentences)).check(matches(withText("3")))
        onView(withId(R.id.tvTotalParagraphs)).check(matches(withText("2")))
    }
}
