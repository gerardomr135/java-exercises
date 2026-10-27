package com.epam.training.todolist.view.commands;

import com.epam.training.todolist.domain.Priority;

import java.time.LocalDate;

public final class CreateCommand extends BaseCommand {
    private final String title;
    private final LocalDate date;
    private final Priority priority;

    public CreateCommand(String title, LocalDate date, Priority priority) {
        super(CommandType.CREATE);
        this.title = title;
        this.date = date;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }
}

