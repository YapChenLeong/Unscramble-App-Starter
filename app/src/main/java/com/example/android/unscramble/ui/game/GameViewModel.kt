package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    /**
    *  Declare private mutable variable that can only be modified within the class it is declared.
     *  */
    private var _currentWordCount = 0
    /**
     * Declare another public immutable field and override its getter method.
     * Return the private property's value in the getter method.
     * When count is accessed, the get() function is called and
     * the value of _count is returned.
     * */
    val currentWordCount: Int //count is public, can access from other classes, like UI controller.
        //get method is being overridden, this property is immutable and read-only
        //when outside class access this property, it returns the value of _count and can't be modified
        //this purpose to protect the app data inside the ViewModel
        get() = _currentWordCount //this _count is private and mutable, only can accessible and editable within the ViewModel class


    //Add backing property to currentScrambledWord
    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord


    //implement final score dialog
    private var _score = 0
    val score: Int
        get() = _score

    // List of words used in the game
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String


    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    /**
    * Updates currentWord and currentScrambledWord with the next word.
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        //Sometimes the shuffled order of characters is the same as the original word.
        //continue loop until the scrambled word is not same as original word
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        //move to next question if the question already exist in the array list,
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else { //
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }


    //Reset all the data words, score, count
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}