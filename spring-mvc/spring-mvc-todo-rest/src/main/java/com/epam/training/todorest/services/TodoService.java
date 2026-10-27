package com.epam.training.todorest.services;

import com.epam.training.todorest.domain.Todo;

import java.util.List;

public interface TodoService {
    Todo create(Todo todo);

    List<Todo> list();

    Todo get(long id);

    void delete(long id);

    Todo update(Todo todo);
}
