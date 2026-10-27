package com.epam.training.person;

public interface Transformer<FROM, TO> {
    public TO transform(FROM value);
}
