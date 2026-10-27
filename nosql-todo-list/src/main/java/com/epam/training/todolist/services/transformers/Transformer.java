package com.epam.training.todolist.services.transformers;

public interface Transformer<IN, OUT> {
    OUT transform(IN value);
}
