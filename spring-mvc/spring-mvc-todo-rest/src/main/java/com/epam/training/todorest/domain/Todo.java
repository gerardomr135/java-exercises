package com.epam.training.todorest.domain;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Todo {

    private long id;

    @NotBlank
    @Size(min = 3, message = "title should be three chars long at least!")
    private String title;

    @NotNull(message = "Date is a required field")
    private LocalDate date;

    @NotNull(message = "Status is a required field")
    private Status status;

    @NotNull(message = "Priority is a required field")
    private Priority priority;

    public Todo(String title, LocalDate date, Status status, Priority priority) {
        this(0, title, date, status, priority);
    }
}
