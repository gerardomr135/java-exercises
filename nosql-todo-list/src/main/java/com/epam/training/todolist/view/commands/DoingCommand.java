package com.epam.training.todolist.view.commands;

public final class DoingCommand extends BaseCommand {
    private final int idx;
    public DoingCommand(int idx) {
        super(CommandType.DOING);
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }
}
