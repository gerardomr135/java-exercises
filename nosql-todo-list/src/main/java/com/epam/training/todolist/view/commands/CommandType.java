package com.epam.training.todolist.view.commands;

public enum CommandType {
    BROKEN(""),
    CREATE("create"),
    DOING("doing"),
    DONE("done"),
    LIST("list"),
    REMOVE("remove"),
    EXIT("exit");

    private final String value;

    CommandType(String value) {
        this.value = value;
    }

    public static CommandType of(String value) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.value.equalsIgnoreCase(value)) {
                return commandType;
            }
        }
        return BROKEN;
    }
}
