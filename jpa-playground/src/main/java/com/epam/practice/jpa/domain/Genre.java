package com.epam.practice.jpa.domain;

public enum Genre {
    MALE,
    FEMALE;

    @Override
    public String toString() {
        return  name().charAt(0) + name().substring(1).toLowerCase();
    }
}
