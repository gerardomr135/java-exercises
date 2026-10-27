package com.epam.training.todolist.view.commands;

public final class DoneCommand extends BaseCommand {

    private final int idx;

    public DoneCommand(int idx) {
        super(CommandType.DONE);
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }
}
