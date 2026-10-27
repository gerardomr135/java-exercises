package com.epam.training.todolist.view;

public interface Printer<T> {
    void print(T value);
    void println(T value);
}
