package com.example.university.lambdas;

import java.util.function.Function;

public class UserFoo {
    public String oldAdd(String string, Foo foo) {
        return foo.method(string);
    }

    public String add(String string, Function<String, String> function) {
        return function.apply(string);
    }
}
