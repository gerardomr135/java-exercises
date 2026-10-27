package com.epam.training.BlockingQueue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class SearchTask implements Runnable {
    private final BlockingQueue<File> queue;
    private final String word;

    public SearchTask(BlockingQueue<File> queue, String word) {
        this.queue = queue;
        this.word = word;
    }

    public void search(File f) throws IOException {
        try(Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains(word)) {
                    System.out.println(f.getPath() + " -> " + line);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                File file = queue.take();
                if (file == FileRunTask.EXIT) {
                    queue.put(file);
                    break;
                }

                search(file);
            }
        } catch (IOException | InterruptedException e)  {
            e.printStackTrace();
        }
    }
}
