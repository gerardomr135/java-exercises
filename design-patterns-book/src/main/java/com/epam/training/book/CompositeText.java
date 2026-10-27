package com.epam.training.book;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeText implements Text {
    List<Text> children = new ArrayList<>();

    public List<Text> getChildren() {
        return children;
    }
}
