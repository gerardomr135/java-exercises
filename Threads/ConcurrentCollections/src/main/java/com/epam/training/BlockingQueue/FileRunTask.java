package com.epam.training.BlockingQueue;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileRunTask implements Runnable {
    public static final File EXIT = new File("");
    private final BlockingQueue<File> queue;
    private final File startDir;

    public FileRunTask(BlockingQueue<File> queue, File startDir) {
        this.queue = queue;
        this.startDir = startDir;
    }

    @Override
    public void run() {
        try {
            runDir(startDir);
            queue.put(EXIT);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runDir(File dir) throws InterruptedException {
        File[] files = dir.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                runDir(file);
                continue;
            }

            queue.put(file);
        }
    }
}
