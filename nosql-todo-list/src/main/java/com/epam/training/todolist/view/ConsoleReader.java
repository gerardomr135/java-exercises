package com.epam.training.todolist.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader implements Reader<String> {
    private static final Scanner SCANNER = new Scanner(System.in);
    @Override
    public String read() {
        String line = SCANNER.nextLine();
        return line;
    }
}
