package com.epam.training.todolist.services.transformers;

import com.epam.training.todolist.domain.Priority;
import com.epam.training.todolist.view.commands.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Component
public class StringToCommandTransformer implements Transformer<String, BaseCommand> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final int COMMAND = 0;
    private static final int ITEM_IDX = 1;
    private static final int PRIORITY = 1;
    private static final int DATE = 2;
    private static final int TITLE = 3;

    @Override
    public BaseCommand transform(String value) {
        String[] parts = value.split(" ");
        CommandType command = CommandType.of(parts[COMMAND]);
        int idx = 0;
        return switch (command) {
            case CREATE -> {
                validateCommandArgumentNumber(parts, TITLE);
                String title = validateTitle(mergeTitle(parts));
                LocalDate date = validateDate(parts[DATE]);
                Priority priority = validatePriority(parts[PRIORITY]);
                yield new CreateCommand(title, date, priority);
            }
            case DOING -> {
                validateCommandArgumentNumber(parts, ITEM_IDX);
                idx = validateIndex(parts[ITEM_IDX]);
                yield new DoingCommand(idx);
            }
            case DONE -> {
                validateCommandArgumentNumber(parts, ITEM_IDX);
                idx = validateIndex(parts[ITEM_IDX]);
                yield new DoneCommand(idx);
            }
            case REMOVE -> {
                validateCommandArgumentNumber(parts, ITEM_IDX);
                idx = validateIndex(parts[ITEM_IDX]);
                yield new RemoveCommand(idx);
            }
            case LIST -> new ListCommand();
            case EXIT -> new ExitCommand();
            default -> new BrokenCommand();
        };
    }

    private String mergeTitle(String[] parts) {
        String[] newParts = new String[parts.length - TITLE];
        System.arraycopy(parts, TITLE, newParts, 0, newParts.length);
        return String.join(" ", newParts);
    }

    private void validateCommandArgumentNumber(String[] parts, int expected) {
        if (parts.length <= expected) {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }
    }

    private String validateTitle(String title) {
        if (Pattern.compile("[a-zA-Z ]+").matcher(title).matches()) {
            return title;
        } else {
            throw new IllegalArgumentException(title + " is not valid!");
        }
    }

    private LocalDate validateDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(date + " is not in an acceptable format: yyyy.MM.dd!");
        }
    }

    private Priority validatePriority(String priority) {
        return "".equals(priority) ? Priority.NO : Priority.of(priority);
    }

    private int validateIndex(String idx) {
        if (Pattern.compile("[0-9]+").matcher(idx).matches()) {
            return Integer.parseInt(idx);
        }
        throw new IllegalArgumentException(idx + " is not valid!");
    }
}
