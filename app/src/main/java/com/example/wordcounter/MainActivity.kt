package com.example.wordcounter

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    // Calculation logic instance
    private val wordCounter = WordCounter()

    // UI elements
    private lateinit var etInputText: TextInputEditText
    private lateinit var btnCalculate: MaterialButton
    private lateinit var btnClear: MaterialButton

    private lateinit var tvTotalWords: TextView
    private lateinit var tvTotalCharacters: TextView
    private lateinit var tvCharsNoSpaces: TextView
    private lateinit var tvTotalSentences: TextView
    private lateinit var tvTotalParagraphs: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
    }

    /**
     * Initializes all UI view references from XML layout.
     */
    private fun initViews() {
        etInputText = findViewById(R.id.etInputText)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnClear = findViewById(R.id.btnClear)

        tvTotalWords = findViewById(R.id.tvTotalWords)
        tvTotalCharacters = findViewById(R.id.tvTotalCharacters)
        tvCharsNoSpaces = findViewById(R.id.tvCharsNoSpaces)
        tvTotalSentences = findViewById(R.id.tvTotalSentences)
        tvTotalParagraphs = findViewById(R.id.tvTotalParagraphs)
    }

    /**
     * Sets up click listeners for Calculate and Clear buttons.
     */
    private fun setupListeners() {
        btnCalculate.setOnClickListener {
            handleCalculate()
        }

        btnClear.setOnClickListener {
            handleClear()
        }
    }

    /**
     * Handles text analysis and displays results.
     * Shows a warning if the input is empty or contains only spaces.
     */
    private fun handleCalculate() {
        val inputText = etInputText.text?.toString().orEmpty()

        if (inputText.trim().isEmpty()) {
            etInputText.error = getString(R.string.msg_empty_input)
            Toast.makeText(this, getString(R.string.msg_empty_input), Toast.LENGTH_SHORT).show()
            resetStatistics()
            return
        }

        etInputText.error = null

        // Perform calculation using the testable WordCounter class
        val stats = wordCounter.calculate(inputText)

        // Display results on UI
        displayStatistics(stats)
    }

    /**
     * Clears user input and resets all statistical counters.
     */
    private fun handleClear() {
        etInputText.error = null
        etInputText.text?.clear()
        etInputText.clearFocus()
        resetStatistics()
        Toast.makeText(this, getString(R.string.msg_cleared), Toast.LENGTH_SHORT).show()
    }

    /**
     * Updates UI TextViews with the calculated statistics.
     */
    private fun displayStatistics(stats: TextStatistics) {
        tvTotalWords.text = stats.words.toString()
        tvTotalCharacters.text = stats.characters.toString()
        tvCharsNoSpaces.text = stats.charactersWithoutSpaces.toString()
        tvTotalSentences.text = stats.sentences.toString()
        tvTotalParagraphs.text = stats.paragraphs.toString()
    }

    /**
     * Resets all statistic counters to zero.
     */
    private fun resetStatistics() {
        val zero = getString(R.string.default_count)
        tvTotalWords.text = zero
        tvTotalCharacters.text = zero
        tvCharsNoSpaces.text = zero
        tvTotalSentences.text = zero
        tvTotalParagraphs.text = zero
    }
}
