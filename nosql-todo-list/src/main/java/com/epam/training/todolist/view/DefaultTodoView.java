package com.epam.training.todolist.view;

import com.epam.training.todolist.services.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultTodoView implements TodoView {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GRAY = "\u001B[90m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";


    private final Printer<String> printer;
    private final Reader<String> reader;

    public DefaultTodoView(Printer<String> printer, Reader<String> reader) {
        this.printer = printer;
        this.reader = reader;
    }

    @Override
    public void printHeader() {
        printer.println("Welcome in the Todo App!");
        printer.println("Available commands:");
        printer.println("  list");
        printer.println("  create <priority> <date> <title>");
        printer.println("  doing <idx>");
        printer.println("  done <idx>");
        printer.println("  remove <idx>");
        printer.println("  exit");
        printer.println("where <idx> is the index of the item in the list.");
        printer.println("where <priority> is one of: NO, LOW, MEDIUM, HIGH.");
        printer.println("where <date> is in the format: yyyy.MM.dd");
    }

    @Override
    public void printExit() {
        printer.println("Good bye!");
    }

    @Override
    public void printPrompt() {
        printer.print("> ");
    }

    @Override
    public void printTodoList(List<Item> items) {
        int idx = 0;
        for (Item item : items) {
            String line = switch (item.status()) {
                case REMOVED -> ANSI_GRAY + item + ANSI_RESET;
                case TO_DO -> switch (item.priority()) {
                    case NO -> item.toString();
                    case LOW -> ANSI_YELLOW + item + ANSI_RESET;
                    case MEDIUM -> ANSI_ORANGE + item + ANSI_RESET;
                    case HIGH -> ANSI_RED + item + ANSI_RESET;
                };
                case DOING -> ANSI_BLUE + item + ANSI_RESET;
                case DONE -> ANSI_GREEN + item + ANSI_RESET;
            };
            printer.println(idx + ": " + line);
            idx++;
        }
    }

    @Override
    public String readCommand() {
        return reader.read();
    }

    @Override
    public void printError(String msg) {
        printer.println(ANSI_RED + msg + ANSI_RESET);
    }
}
