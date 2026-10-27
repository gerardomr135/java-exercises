package com.epam.training.todolist.view;

import com.epam.training.todolist.services.Item;

import java.util.List;

public interface TodoView {

    void printHeader();

    void printExit();

    void printPrompt();

    void printTodoList(List<Item> items);

    String readCommand();

    void printError(String msg);
}
