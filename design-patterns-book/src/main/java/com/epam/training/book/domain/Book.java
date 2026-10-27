package com.epam.training.book.domain;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.book.CompositeText;
import com.epam.training.book.Text;

public class Book extends CompositeText {
    private final String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addPart(Part part) {
        part.setParent(this);
        getChildren().add(part);
    }

    @Override
    public int getNumberOfWords() {
        var totalWords = 0;

        for (Text part : getChildren()) {
            totalWords += part.getNumberOfWords();
        }

        return totalWords;
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        List<Verse> versesFound = new ArrayList<>();

        for (Text part : getChildren()) {
            versesFound.addAll(part.getVersesContainingWord(word));
        }

        return versesFound;
    }
}
