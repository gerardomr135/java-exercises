package com.example.university.lambdas;

import java.util.function.*;

@FunctionalInterface
public interface Foo {
    String method(String  string);
    default String defaultMethod(String string) {
        return string;
    }
}
