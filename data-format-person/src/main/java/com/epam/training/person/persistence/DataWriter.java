package com.epam.training.person.persistence;

public interface DataWriter<DATA_TYPE> extends AutoCloseable {
    public void write(DATA_TYPE value);
}
