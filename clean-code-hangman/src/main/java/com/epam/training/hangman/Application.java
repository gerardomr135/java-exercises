package com.epam.training.hangman;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // Init
        // Warning: don't close this Scanner, that would close System.in as well,
        // making it inaccessible throughout the rest of the program.
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        String[] words = {"hangman", "apple", "bee", "clean", "computer", "office", "recursion"};

        run(scanner, words);
    }

    public static void run(Scanner scanner, String[] words) {
        String word = Utils.choiceRandomWord(words);

        HangmanLogic game = new HangmanLogic(word);

        Utils.printInitialMessage();

        // main cycle
        do {
            Utils.printGameStatus(game);

            // inner cycle
            while (true) {
                String in = Utils.requestUserInput(scanner);
                try {
                    // guessing
                    game.guess(in.charAt(0));
                    break;
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }
        } while (game.getState() == State.IN_PROGRESS);

        Utils.printFinalMessage(game, word);
    }
}