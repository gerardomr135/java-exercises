package com.epam.training.todolist.domain;

public enum Priority {
    NO, LOW, MEDIUM, HIGH;

    public static Priority of(String value) {
        for (Priority priority : Priority.values()) {
            if (priority.name().equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException(value + " is not an existing priority!");
    }
}
