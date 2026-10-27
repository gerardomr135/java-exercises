package com.epam.training.book;

import java.util.List;

import com.epam.training.book.domain.AllBooks;
import com.epam.training.book.domain.Verse;

public class Application {
    public static void main(String[] args) {
        AllBooks allBooks = TestDataCreator.createAllBooks();

        // word count
        int numberOfWords = allBooks.getNumberOfWords();
        System.out.println("Number of all words: " + numberOfWords);

        // contain word
        List<Verse> verses = allBooks.getVersesContainingWord("God");
        System.out.println("Verses containing the word 'God':");
        for (Verse v : verses) {
            System.out.println(v.format());
        }
    }

}
