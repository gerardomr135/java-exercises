package com.epam.training.travel.domain;

public enum Category {
    NATURE("Nature"),
    RELAXATION("Relaxation"),
    SHOPPING("Shopping"),
    CULTURE("Culture"),
    ARCHITECTURE("Architecture");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
