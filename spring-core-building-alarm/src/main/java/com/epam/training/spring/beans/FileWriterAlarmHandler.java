package com.epam.training.spring.beans;

import com.epam.training.spring.model.Alarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterAlarmHandler implements AlarmHandler, Lifecycle {
    private static final Logger LOG = LoggerFactory.getLogger("AlarmWriter");

    @Value("${alarm.file.path}")
    private String filePath;
    private BufferedWriter writer;
    private boolean running = false;

    @Override
    public void start() {
        LOG.info("AlarmWriter has been started");
        try {
            this.writer = new BufferedWriter(new FileWriter(filePath, true));
            running = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        LOG.info("AlarmWriter has been stopped");
        try {
            if (writer != null) {
                writer.close();
            }
            running = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void handleAlarm(Alarm alarm) {
        LOG.info("Alarm has been handled");
        try {
            writer.write(alarm.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
