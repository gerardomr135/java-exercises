package com.epam.training.todolist.services;

import com.epam.training.todolist.dataaccess.documents.Todo;
import com.epam.training.todolist.dataaccess.documents.TodoRepository;
import com.epam.training.todolist.domain.Status;
import com.epam.training.todolist.services.transformers.Transformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultTodoService implements TodoService {
    private final TodoRepository todoRepository;
    private final Transformer<Item, Todo> itemToTodoTransformer;
    private final  Transformer<Todo, Item> todoToItemTransformer;

    public DefaultTodoService(TodoRepository todoRepository, Transformer<Item, Todo> itemToTodoTransformer, Transformer<Todo, Item> todoToItemTransformer) {
        this.todoRepository = todoRepository;
        this.itemToTodoTransformer = itemToTodoTransformer;
        this.todoToItemTransformer = todoToItemTransformer;
    }

    @Override
    public List<Item> list() {
        List<Item> items = new ArrayList<>();

        for (Todo todo : todoRepository.findAll()) {
            items.add(todoToItemTransformer.transform(todo));
        }

        return items;
    }

    @Override
    public void create(Item item) {
        todoRepository.save(itemToTodoTransformer.transform(item));
    }

    @Override
    public void remove(String id) {
        Optional<Todo> res = todoRepository.findById(id);

        if (res.isEmpty()) {
            return;
        }

        Item item = todoToItemTransformer.transform(res.get());
        todoRepository.delete(itemToTodoTransformer.transform(item));
    }

    @Override
    public void done(String id) {
        Optional<Todo> res = todoRepository.findById(id);

        if (res.isEmpty()) {
            return;
        }

        Todo todo = res.get();
        todo.setStatus(Status.DONE);
        todoRepository.save(todo);
    }

    @Override
    public void doing(String id) {
        Optional<Todo> res = todoRepository.findById(id);

        if (res.isEmpty()) {
            return;
        }

        Todo todo = res.get();
        todo.setStatus(Status.DOING);
        todoRepository.save(todo);
    }
}
