package com.epam.training.smarthome.domain.observer;

import java.util.ArrayList;
import java.util.List;

public class MessageObserver implements Observer {
    private final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void update(String message) {
        messages.add(message);
    }

    public void printOffset(int offset) {
        if (offset > messages.size()) {
            return;
        }

        for (int i = offset; i < messages.size(); i++) {
            String element = messages.get(i);
            System.out.println(element);
        }
    }
}
