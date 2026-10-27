package com.epam.training.todolist.services;

import java.util.List;

public interface TodoService {
    List<Item> list();
    void create(Item item);
    void remove(String id);
    void done(String id);
    void doing(String id);
}
