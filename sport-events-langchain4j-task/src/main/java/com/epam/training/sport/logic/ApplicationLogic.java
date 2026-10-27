package com.epam.training.sport.logic;

import com.epam.training.sport.domain.SportEvent;
import com.epam.training.sport.service.EventExtractionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ApplicationLogic implements CommandLineRunner {
    private final EventExtractionService eventExtractionService;

    public ApplicationLogic(EventExtractionService eventExtractionService) {
        this.eventExtractionService = eventExtractionService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please provide the input file path as a command line argument.");
            return;
        }
        String eventText = readFile(args[0]);
        SportEvent event = eventExtractionService.extract(eventText);
        System.out.println(event);
    }

    private static String readFile(String file) {
        try {
            return Files.readString(Path.of(file));
        } catch (IOException e) {
            throw new RuntimeException("Error happened when reading file: " + file, e);
        }
    }
}
