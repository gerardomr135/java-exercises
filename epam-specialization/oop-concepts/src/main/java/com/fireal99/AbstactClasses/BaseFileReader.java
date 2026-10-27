package com.fireal99.AbstactClasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public abstract class BaseFileReader {
    protected Path filePath;

    public BaseFileReader(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public List<String> readFile() throws IOException {
        try (Stream<String> stream = Files.lines(filePath)) {
            return stream.map(this::mapFileLine).toList();
        }
    }

    protected abstract String mapFileLine(String line);
}
