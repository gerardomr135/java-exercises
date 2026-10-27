package com.fireal99.Interfaces;

import java.io.IOException;
import java.io.InputStream;

public class Computer implements Electronic, IO {

    @Override
    public void getElectricityUse() {
    }

    @Override
    public void printDescription() {
        Electronic.super.printDescription();
    }

    @Override
    public void conflict() {
    }

    @Override
    public void defaultConflict() {
        Electronic.super.defaultConflict();
        IO.super.defaultConflict();
    }

    @Override
    public void write(String data) {
        System.out.println(data);
    }

    @Override
    public byte[] read(InputStream is) throws IOException {
        return is.readAllBytes();
    }
}
