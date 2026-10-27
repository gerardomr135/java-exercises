package com.epam.training.hangman;

import java.util.ArrayList;
import java.util.List;

public class HangmanLogic {
    public String word;
    public List<Character> lettersTried;
    public int wrongGuessCount;

    public final int MAX_GUESSES;
    public State state;

    public HangmanLogic(String word) {
        validateWord(word);

        this.word = word;
        this.lettersTried = new ArrayList<>();
        this.wrongGuessCount = 0;
        this.state = State.IN_PROGRESS;
        this.MAX_GUESSES = 7;
    }

    public void guess(char c) {
        validateWord(String.valueOf(c));

        if (getLettersTried().contains(c)) {
            throw new IllegalArgumentException("Error: letter has already been guessed!");
        }

        // logic
        getLettersTried().add(c);
        if (word.contains(String.valueOf(c))) {
           if (isWon()) setState(State.WON);
           return;
        }

        wrongGuessCount++;
        if (isLost()) setState(State.LOST);
    }

    public String getDisplayedWord() {
        if(isLost()){
            return word;
        }

        StringBuilder dw = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (lettersTried.contains(word.charAt(i))) {
                dw.append(word.charAt(i));
            } else {
                dw.append('_');
            }
        }

        return dw.toString();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Character> getLettersTried() {
        return lettersTried;
    }

    public int getWrongGuessesLeft() {
        return MAX_GUESSES - wrongGuessCount;
    }

    private void validateWord(String word) {
        if (word == null) throw new IllegalArgumentException("Error: word cannot be null!");
        if (word.isEmpty()) throw new IllegalArgumentException("Error: word cannot be empty!");

        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLowerCase(word.charAt(i))) {
                throw new IllegalArgumentException("Error: word must be a lowercase letter!");
            }
        }
    }

    private boolean isWon() {
        int tries = 0;

        for (int i = 0; i < word.length(); i++) {
            if (lettersTried.contains(word.charAt(i))) {
                tries = tries + 1;
            }
        }

        return tries == word.length();
    }

    private boolean isLost() {
        return getWrongGuessesLeft() == 0;
    }
}