package com.epam.training.book.domain;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.book.CompositeText;
import com.epam.training.book.Text;

public class Part extends CompositeText {
    private Book parent;
    private final int number;

    public Part(int number) {
        this.number = number;
    }

    public void setParent(Book parent) {
        this.parent = parent;
    }

    public Book getParent() {
        return parent;
    }

    public int getNumber() {
        return number;
    }

    public void addVerse(Verse verse) {
        verse.setParent(this);
        getChildren().add(verse);
    }

    @Override
    public int getNumberOfWords() {
        var totalWords = 0;

        for (Text verse : getChildren()) {
            totalWords += verse.getNumberOfWords();
        }

        return totalWords;
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        List<Verse> versesFound = new ArrayList<>();

        for (Text verse : getChildren()) {
            versesFound.addAll(verse.getVersesContainingWord(word));
        }

        return versesFound;
    }

}
