package com.epam.training.todolist.dataaccess.documents;

import com.epam.training.todolist.domain.Priority;
import com.epam.training.todolist.domain.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Setter
@Getter
@Document
public class Todo {
    @Id
    private String id;
    private String title;
    private LocalDate date;
    private Status status;
    private Priority priority;

    public Todo(String id, String title, LocalDate date, Status status, Priority priority) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
        this.priority = priority;
    }
}
