package com.epam.training.book.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.training.book.Text;

public class Verse implements Text {
    private Part parent;
    private final int number;
    private final String content;

    public Verse(int number, String content) {
        this.number = number;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }

    public void setParent(Part parent) {
        this.parent = parent;
    }

    public String format() {
        return String.format("%s %d,%d \"%s\"",
                parent.getParent().getTitle(),
                parent.getNumber(),
                number,
                content);
    }

    @Override
    public int getNumberOfWords() {
        return (int) Arrays.stream(content.trim().split("\\W+"))
                .filter(word -> !word.isEmpty())
                .count();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        List<Verse> verses = new ArrayList<>();

        String regex = "\\b" + Pattern.quote(word) + "\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find())
            verses.add(this);

        return verses;
    }
}
