package com.epam.rd.autotask.thread;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("java:S4929")
public class EnterKeyInputStream extends InputStream {
    // Place your code here
    private final long interval;
    private boolean enterPressed = false;

    public EnterKeyInputStream(long interval) {
        // Place your code here
        this.interval = interval;
    }

    @Override
    public int read() throws IOException {
        if (!enterPressed) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            }

            enterPressed = true;
            return '\n'; // The ASCII integer value for the newline/Enter character
        }

        return -1;
    }
}
