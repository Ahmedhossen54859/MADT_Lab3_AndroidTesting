# MADT Lab #3: Android Testing – Word Counter App

## 📱 Introduction & Project Overview

**MADT Lab #3** is an Android application developed to demonstrate comprehensive mobile software testing practices, including modular software design, **Unit Testing** on the JVM, and **UI / Instrumentation Testing** using **AndroidX Test** and **Espresso**.

The application is a **Word & Text Statistics Counter** that allows users to input any block of text and instantly calculate detailed metric analysis:
- **Total Words**
- **Total Characters (including spaces and newlines)**
- **Characters without Spaces**
- **Total Sentences**
- **Total Paragraphs**

The core business logic is encapsulated in a pure Kotlin class (`WordCounter.kt`), cleanly decoupled from Android framework dependencies, ensuring fast execution, high testability, and 100% test coverage.

---

## ✨ Features

- **Real-Time Text Analysis**: Accurately computes words, characters, characters excluding whitespace, sentences, and paragraphs.
- **Robust Edge-Case Handling**:
  - Handles empty strings, consecutive spaces, tabs, and newlines.
  - Supports multiple sentence terminators (`.`, `!`, `?`, ellipses `...`).
  - Correctly parses Unix (`\n`) and Windows (`\r\n`) paragraph breaks, ignoring empty lines.
- **Clean Material Design UI**: Modern interface built with Material Components (`TextInputLayout`, `TextInputEditText`, `MaterialButton`, and metric cards).
- **Interactive Validation**:
  - Displays input field warnings and Toast alerts when the user attempts calculation on empty or whitespace-only input.
  - "Clear" button resets all statistical counters and clears the input field.

---

## 🛠️ Technologies & Tools

- **Programming Language**: [Kotlin](https://kotlinlang.org/)
- **Target Platform**: Android (Minimum SDK: 24, Target SDK: 34, Compile SDK: 37)
- **Build System**: Gradle 9.6 with Kotlin DSL (`build.gradle.kts`)
- **UI Components**: AndroidX AppCompat, Material Components for Android
- **Unit Testing**: [JUnit 4](https://junit.org/junit4/)
- **UI & Instrumentation Testing**: [AndroidX Test](https://developer.android.com/training/testing), [Espresso Core](https://developer.android.com/training/testing/espresso)
- **Code Coverage**: [JaCoCo](https://www.jacoco.org/jacoco/) (Java Code Coverage library)

---

## 🧪 Unit Testing & Business Logic Coverage

### Pure Kotlin Business Logic (`WordCounter.kt`)
The core calculation logic is isolated from Android SDK dependencies:
- `WordCounter`: Handles parsing, counting algorithms, and orchestration.
- `TextStatistics`: Immutable data class encapsulating the metrics.

### Test Suite (`WordCounterTest.kt`)
Contains **23 unit test cases** covering positive scenarios, boundary values, and edge cases:
1. **Word Counting (`countWords`)**: Empty text, whitespace-only, single word, multiple words with single spaces, multiple consecutive whitespaces/tabs/newlines, hyphens, and numbers.
2. **Character Counting (`countCharacters`)**: Empty text, whitespace characters, multiline strings, Unicode, and emojis.
3. **Characters without Spaces (`countCharactersWithoutSpaces`)**: Empty string, whitespace-only strings, mixed spacing, long continuous strings.
4. **Sentence Counting (`countSentences`)**: Empty input, punctuation-only strings (`...`, `!??`), single sentences without punctuation, single sentences with various punctuation marks, multiple sentences, multiline sentences.
5. **Paragraph Counting (`countParagraphs`)**: Empty text, whitespace-only, single line, multiple paragraphs with `\n` or `\r\n`, multiple blank lines, lines containing whitespace.
6. **Aggregate Statistics & Data Class (`calculate` / `TextStatistics`)**: All-zero defaults, whitespace inputs, full multiline text analysis, property equality, hash code verification, destructuring declarations, and string representations.

### 💯 100% Business Logic Coverage Confirmed
According to the official **JaCoCo Coverage Report** (`app/build/reports/coverage/test/debug/report.xml`):

| Class | Instruction Coverage | Branch Coverage | Line Coverage | Method Coverage | Class Coverage |
| :--- | :---: | :---: | :---: | :---: | :---: |
| `WordCounter` | **100%** (180/180) | **100%** (18/18) | **100%** (27/27) | **100%** (7/7) | **100%** (1/1) |
| `TextStatistics` | **100%** (33/33) | N/A | **100%** (6/6) | **100%** (6/6) | **100%** (1/1) |
| **Total `WordCounter.kt`** | **100%** (213/213) | **100%** (18/18) | **100%** (33/33) | **100%** (13/13) | **100%** (2/2) |

---

## 🎭 Five UI Test Scenarios (Espresso)

Automated Android UI tests are implemented in `WordCounterUITest.kt` using Espresso and `ActivityScenario`. All five scenarios pass successfully (verified in `ui_test_results.txt`):

### Scenario 1: Normal Text Entry & Full Metric Verification
- **Test Method**: `testScenario1_enterNormalText_verifiesAllCalculatedResults`
- **Actions**: Types `"The quick brown fox jumps over the lazy dog. It was a sunny day."` and clicks **Calculate**.
- **Assertions**:
  - Total Words: `14`
  - Total Characters: `64`
  - Characters Without Spaces: `51`
  - Total Sentences: `2`
  - Total Paragraphs: `1`

### Scenario 2: Irregular & Multiple Spacing Handling
- **Test Method**: `testScenario2_enterTextWithMultipleSpaces_verifiesWordCounting`
- **Actions**: Types `"   Android    Studio   Espresso   "` (multiple leading, middle, and trailing spaces) and clicks **Calculate**.
- **Assertions**:
  - Total Words: `3`
  - Total Characters: `34`
  - Characters Without Spaces: `21`
  - Total Sentences: `1`
  - Total Paragraphs: `1`

### Scenario 3: Empty Input Validation & State Reset
- **Test Method**: `testScenario3_clickCalculateWithEmptyInput_verifiesWarningAndReset`
- **Actions**: Enters initial text, clears the field completely, and clicks **Calculate**.
- **Assertions**:
  - Validates that an error message is set on the input field: `"Please enter some text to calculate."`.
  - Validates that all output metric counters reset to `"0"`.

### Scenario 4: Clear Button Functionality
- **Test Method**: `testScenario4_clickClear_verifiesInputAndResultsReset`
- **Actions**: Types `"Sample test text for clear."`, clicks **Calculate**, then clicks the **Clear** button.
- **Assertions**:
  - Validates that the input text field is cleared (`""`).
  - Validates that all output statistics are reset to `"0"`.

### Scenario 5: Multiline Text with Multiple Sentences & Paragraphs
- **Test Method**: `testScenario5_enterMultilineText_verifiesSentencesAndParagraphs`
- **Actions**: Types `"First paragraph sentence one. Sentence two!\n\nSecond paragraph sentence three?"` and clicks **Calculate**.
- **Assertions**:
  - Total Words: `10`
  - Total Characters: `77`
  - Characters Without Spaces: `67`
  - Total Sentences: `3`
  - Total Paragraphs: `2`

---

## 🚀 Test Execution Instructions

### Running Unit Tests & Generating Coverage Report
To run all unit tests from the command line:
```bash
./gradlew testDebugUnitTest
```

To run unit tests and generate the JaCoCo coverage report:
```bash
./gradlew testDebugUnitTestCoverage
```
The HTML coverage report will be generated at:
```
app/build/reports/coverage/test/debug/index.html
```

### Running Espresso UI Tests
To run instrumentation tests on an active Android device or emulator:
```bash
./gradlew connectedDebugAndroidTest
```
Alternatively, execute via Android Debug Bridge (ADB):
```bash
adb shell am instrument -w -r com.example.wordcounter.test/androidx.test.runner.AndroidJUnitRunner
```

---

## 📂 Project Structure

```
MADT_Lab3_AndroidTesting/
├── .gitignore                                # Git ignore configuration
├── README.md                                 # Project documentation
├── build.gradle.kts                          # Top-level Gradle configuration
├── gradle.properties                         # Gradle JVM & build properties
├── settings.gradle.kts                       # Repository & project module settings
├── ui_test_results.txt                       # Instrumentation UI test execution log
├── gradle/
│   ├── libs.versions.toml                    # Version catalog for dependencies
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── repo/                                     # Local dependency repository (JaCoCo)
└── app/
    ├── build.gradle.kts                      # App-level build config with coverage enabled
    ├── proguard-rules.pro                    # Proguard rules for release build
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── java/com/example/wordcounter/
        │   │   ├── MainActivity.kt           # Main UI Controller & View logic
        │   │   └── WordCounter.kt            # Pure Kotlin business logic & data models
        │   └── res/
        │       ├── layout/
        │       │   └── activity_main.xml     # UI layout with Material Design
        │       └── values/
        │           ├── colors.xml
        │           ├── strings.xml
        │           └── themes.xml
        ├── test/
        │   └── java/com/example/wordcounter/
        │       └── WordCounterTest.kt        # 23 Unit tests with 100% logic coverage
        └── androidTest/
            └── java/com/example/wordcounter/
                └── WordCounterUITest.kt      # 5 Espresso UI test scenarios
```

---

## 👤 Author
- **Course**: Mobile Application Development Technologies (MADT)
- **Lab**: Lab #3 – Android Testing
- **Student**: Ahmed Hossen
- **GitHub**: [Ahmedhossen54859](https://github.com/Ahmedhossen54859)
