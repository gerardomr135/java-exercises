package com.fireal99.AbstactClasses;

import java.nio.file.Path;

public class UppercaseFileReader extends BaseFileReader {
    public UppercaseFileReader(Path filePath) {
        super(filePath);
    }

    @Override
    protected String mapFileLine(String line) {
        return line.toUpperCase();
    }
}
