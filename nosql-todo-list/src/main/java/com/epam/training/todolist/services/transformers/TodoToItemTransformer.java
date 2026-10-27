package com.epam.training.todolist.services.transformers;

import com.epam.training.todolist.dataaccess.documents.Todo;
import com.epam.training.todolist.services.Item;
import org.springframework.stereotype.Component;

@Component
public class TodoToItemTransformer implements Transformer<Todo, Item> {
    @Override
    public Item transform(Todo value) {
        return new Item(
          value.getId(),
          value.getTitle(),
          value.getDate(),
          value.getStatus(),
          value.getPriority()
        );
    }
}
