package com.epam.training.book.domain;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.book.CompositeText;
import com.epam.training.book.Text;

public class AllBooks extends CompositeText {
    public void addBook(Book book) {
        getChildren().add(book);
    }

    public int getNumberOfWords() {
        var totalWords = 0;

        for (Text book : getChildren()) {
            totalWords += book.getNumberOfWords();
        }

        return totalWords;
    }

    public List<Verse> getVersesContainingWord(String searchWord) {
        List<Verse> versesFound = new ArrayList<>();

        for (Text book : getChildren()) {
            versesFound.addAll(book.getVersesContainingWord(searchWord));
        }

        return versesFound;
    }
}
