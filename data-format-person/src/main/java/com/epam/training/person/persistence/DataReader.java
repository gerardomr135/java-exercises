package com.epam.training.person.persistence;

public interface DataReader<DATA_TYPE> extends AutoCloseable {
    public DATA_TYPE read();
}
