package com.epam.training.todolist.services.transformers;

import com.epam.training.todolist.dataaccess.documents.Todo;
import com.epam.training.todolist.services.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemToTodoTransformer implements Transformer<Item, Todo> {
    @Override
    public Todo transform(Item value) {
        return new Todo(
                value.id(),
                value.title(),
                value.date(),
                value.status(),
                value.priority()
        );
    }
}
