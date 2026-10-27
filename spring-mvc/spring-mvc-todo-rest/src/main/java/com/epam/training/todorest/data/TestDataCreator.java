package com.epam.training.todorest.data;

import com.epam.training.todorest.domain.Todo;
import com.epam.training.todorest.services.TodoService;
import com.epam.training.todorest.domain.Priority;
import com.epam.training.todorest.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestDataCreator implements CommandLineRunner {

    private final TodoService todoService;

    @Override
    public void run(String... args) throws Exception {
        todoService.create(
                new Todo("Remember the milk", LocalDate.now().minusDays(2), Status.DONE, Priority.MEDIUM));
        todoService.create(
                new Todo("Kids at school", LocalDate.now().plusDays(2), Status.TO_DO, Priority.HIGH));
        todoService.create(
                new Todo("Walk the dog", LocalDate.now().plusWeeks(1), Status.TO_DO, Priority.LOW));
        todoService.create(
                new Todo("Gym", LocalDate.now(), Status.DOING, Priority.NO));
        todoService.create(
                new Todo("Gardening", LocalDate.now().plusWeeks(3), Status.TO_DO, Priority.NO));
        todoService.create(
                new Todo("Grocery", LocalDate.now().plusDays(2), Status.TO_DO, Priority.MEDIUM));
        todoService.create(
                new Todo("Tickets to the show", LocalDate.now().plusMonths(3), Status.TO_DO, Priority.HIGH));
    }
}
