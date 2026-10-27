package com.epam.training.todolist.services;

import com.epam.training.todolist.domain.Priority;
import com.epam.training.todolist.domain.Status;

import java.time.LocalDate;

public record Item(
        String id,
        String title,
        LocalDate date,
        Status status,
        Priority priority
) {
}
