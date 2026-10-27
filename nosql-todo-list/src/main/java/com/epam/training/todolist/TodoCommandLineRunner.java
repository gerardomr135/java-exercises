package com.epam.training.todolist;

import com.epam.training.todolist.domain.Status;
import com.epam.training.todolist.services.Item;
import com.epam.training.todolist.services.TodoService;
import com.epam.training.todolist.view.commands.*;
import com.epam.training.todolist.services.transformers.Transformer;
import com.epam.training.todolist.view.TodoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TodoCommandLineRunner implements CommandLineRunner {

    private final TodoView todoView;
    private final Transformer<String, BaseCommand> transformer;
    private final TodoService service;

    @Autowired
    public TodoCommandLineRunner(TodoView todoView, Transformer<String, BaseCommand> transformer, TodoService service) {
        this.todoView = todoView;
        this.transformer = transformer;
        this.service = service;
    }

    @Override
    public void run(String... args) {
        todoView.printHeader();

        BaseCommand cmd;
        boolean isTerminated = false;
        do {
            todoView.printPrompt();
            try {
                cmd = transformer.transform(todoView.readCommand());
                isTerminated = handleCommand(cmd, isTerminated);
            } catch (Exception e) {
                todoView.printError(e.getMessage());
            }
        } while (!isTerminated);

        todoView.printExit();
    }

    private boolean handleCommand(BaseCommand cmd, boolean isTerminated) {
        if (cmd instanceof CreateCommand todo) {
            service.create(new Item(null, todo.getTitle(), todo.getDate(), Status.TO_DO, todo.getPriority()));
        } else if (cmd instanceof DoingCommand doing) {
            service.doing(getItemById(doing.getIdx()).id());
        } else if (cmd instanceof DoneCommand done) {
            service.done(getItemById(done.getIdx()).id());
        } else if (cmd instanceof RemoveCommand remove) {
            service.remove(getItemById(remove.getIdx()).id());
        } else if (cmd instanceof ListCommand) {
            todoView.printTodoList(service.list());
        } else if (cmd instanceof ExitCommand) {
            isTerminated = true;
        } else {
            todoView.printError("Broken command!");
        }
        return isTerminated;
    }

    private Item getItemById(int index) {
        if (service.list().isEmpty()) {
            throw new IllegalArgumentException("The Todo list is empty!");
        }
        try {
            return service.list().get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid index! Please provide a valid index between 0 and " + (service.list().size() - 1));
        }
    }
}
