import java.util.Random
import java.io.File
import java.io.InputStream
import java.util.ArrayList

/**
 * https://www.nytimes.com/games/wordle/index.html
 * https://wordplay.com/
 * Author: Seikyung Jung
 * Description: I was able to get to the very last function of the project gameOver() and
 * called selectWord() which is step 1 of the last function but wasn't able to get to the rest
 * However all the other functions previous all work
 *
 */

// Place ANSI escape sequences in a string to change background color. For example,
// "$ANSI_GREEN$word$ANSI_RESET"
// https://en.wikipedia.org/wiki/ANSI_escape_code
const val ANSI_RESET = "\u001B[0m"   // Reset to default background color
const val ANSI_GREEN = "\u001B[42m"  // Green background color
const val ANSI_YELLOW = "\u001B[43m" // Yellow background color
const val ANSI_BLACK = "\u001B[40m"  // Black background color

// Read a file (list of words) used in the game
val wordList = File("wordle.txt").readLines()

// Pick a word from the file, randomly
fun selectWord():String{
    val randomWord = wordList.random()
    return randomWord
}

// Check if user's word exists in the file
fun legitGuess(guess:String):Boolean{
    val selectedWord = selectWord()

    return guess == selectedWord
}

fun countCharacterOccurrences(str:String):MutableMap<Char, Int>{

    val wordMap = mutableMapOf<Char, Int>()
    // loop through each letter in the random word
    for (letter in str){
        wordMap[letter] = wordMap.getOrDefault(letter,0) + 1
    }
    return wordMap
}

// Color-code user's guess using information about the selected word
// 1. Store color-coded user's guess as array of strings. Five letters, index 0 to 4.
// 2. Count occurrences of each letter in the word
// 3. First, highlight in green where the characters lined up properly
//    If the guessed letter matches the word letter, color code in green
//    and decrement the occurrences for the corresponding letter
// 4. Next, highlight letters in the word but in the wrong spot or non-matches
//    If the guessed letter is in the word, highlight in yellow
//    and decrement the occurrences for the corresponding letter
//    Otherwise, highlight non-matches with a black background
// 5. Return the game state (remember to reset the background color)

fun gameState(guess: String, word: String):String{
    var gamestate = ""
    val guessArray = guess.toCharArray()
    val wordArray = word.toCharArray()
    var occurrences = countCharacterOccurrences(word)

    for (i in 0 until 5){
        val guessLetter = guessArray[i]
        if (guessArray[i] == wordArray[i]){
            gamestate += "$ANSI_GREEN$guessLetter$ANSI_RESET"
            occurrences[guessLetter] = occurrences.getOrDefault(guessLetter,0) - 1
        }
        else if (occurrences.getOrDefault(guessLetter,0) > 0 ){
            gamestate += "$ANSI_YELLOW$guessLetter$ANSI_RESET"
            occurrences[guessLetter] = occurrences.getOrDefault(guessLetter,0) - 1
        }
        else{
            gamestate += "$ANSI_BLACK$guessLetter$ANSI_RESET"
        }
    }
    return gamestate
}

// Determine when the game is over and print out the game state.
// If the game is over, congratulate the user
fun gameOver(userInput: String, word: String): Boolean{

    selectWord()

    return true
}

// 1. call selectWord()
// 2. print selected word (for debugging, show the word to guess)
// 3. Only allow 6 chances to guess based on original Wordle game
//    call legitGuess()
//    If the game is over, exit
// 4. If the user didn't guess the word, show it to the user

fun main(){
    //Make sure that your guess and your word are both 5-letter words otherwise it will break the code
    println(gameState("sleep","guess"))
    println(gameState("words", "works"))


}
