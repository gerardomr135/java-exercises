package com.fireal99.Interfaces;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class InterfacesTest {
    @Test
    public void testInterfaces() throws Exception {
        Computer computer = new Computer();

        byte[] bytes = "Hello, world!".getBytes(StandardCharsets.UTF_8);

        // 2. Wrap the byte array in a ByteArrayInputStream

        InputStream inputStream = new ByteArrayInputStream(bytes);

        computer.read(inputStream);

        computer.getElectricityUse();

        computer.write("xD");
    }
}
