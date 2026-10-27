package com.epam.training.hangman;

import java.util.Random;
import java.util.Scanner;

public abstract class Utils {
    public static void printGameStatus(HangmanLogic game) {
        System.out.println("The word: " + game.getDisplayedWord());
        System.out.println("Letters tried: " + game.lettersTried);
        System.out.println("Wrong guesses until game over: " + game.getWrongGuessesLeft());
    }

    public static void printInitialMessage() {
        System.out.println("Welcome to the Hangman game!");
        System.out.println();
    }

    public static void printFinalMessage(HangmanLogic game, String word) {
        if (game.getState()== State.WON) {
            System.out.println("Congratulations, you won!");
            System.out.println("The word was: " + word);
            return;
        }

        System.out.println("You have no more tries left, you lost the game");
        System.out.println("The word was: " + word);
    }

    public static String choiceRandomWord(String[] words) {
        Random r = new Random();
        int index = r.nextInt(words.length);
        return words[index];
    }

    public static String requestUserInput(Scanner scanner) {
        String in = "";

        boolean stay = true;
        while (stay) {
            System.out.print("Enter your guess: ");
            in = scanner.next();
            System.out.println();
            if (in.length() == 1) stay = false;
            else {
                System.out.println("Error: please enter a single character only!");
                System.out.println();
            }
        }

        return in;
    }
}
