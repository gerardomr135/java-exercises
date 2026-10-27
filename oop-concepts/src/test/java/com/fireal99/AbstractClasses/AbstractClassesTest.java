package com.fireal99.AbstractClasses;

import com.fireal99.AbstactClasses.BaseFileReader;
import com.fireal99.AbstactClasses.LowercaseFileReader;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractClassesTest {
    @Test
    public void testAbstractClasses() throws Exception {
        URL location = getClass().getClassLoader().getResource("text.txt");

        assertNotNull(location);

        Path path = Paths.get(location.toURI());

        BaseFileReader lowercaseFileReader = new LowercaseFileReader(path);

        List<String> lines = lowercaseFileReader.readFile();

        assertInstanceOf(List.class, lines);

        assertEquals(lines, Arrays.asList("hello, world!", "hello, world 2"));
    }
}
