package com.epam.training.todolist.view.commands;

public final class RemoveCommand extends BaseCommand {

    private final int idx;
    public RemoveCommand(int idx) {
        super(CommandType.REMOVE);
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }
}
