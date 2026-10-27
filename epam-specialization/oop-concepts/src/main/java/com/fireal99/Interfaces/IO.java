package com.fireal99.Interfaces;

public interface IO extends Input, Output {
    void conflict();
    default void defaultConflict() {
    }
}
