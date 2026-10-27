package com.epam.training.todorest.services;

import com.epam.training.todorest.domain.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoServiceImp implements TodoService {
    List<Todo> todos = new ArrayList<>();

    public Todo create(Todo todo) {
        long id = 0;
        if (!todos.isEmpty()) {
            id = todos.get(todos.size() - 1).getId() + 1;
        }
        todo.setId(id);
        todos.add(todo);
        return todo;
    }

    public List<Todo> list() {
        return todos;
    }

    public Todo get(long id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst().orElseThrow(
                        () -> new NoSuchElementException("Todo item with id " + id + " does not exist."));
    }

    public void delete(long id) {
        todos.removeIf(todo -> todo.getId() == id);
    }

    public Todo update(Todo todo) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == todo.getId()) {
                todos.set(i, todo);
                break;
            }
        }
        return todo;
    }
}
