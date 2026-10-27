package com.fireal99;

import java.io.Closeable;
import java.io.IOException;

public class Reource implements Closeable {
    @Override
    public void close() throws IOException {
        System.out.println("Closing Reource");
        throw  new IOException();
    }
}
