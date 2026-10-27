package com.fireal99.Exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ExceptionsTest {
    @Test
    public void exceptionTest() {
        Assertions.assertDoesNotThrow(()->{
            var res = Exceptions.handleException();
            System.out.println(res);
        });
    }

    @Test
    public void handleTryWithResourcesTest() {
        Exceptions.handleTryWithResources();
    }
}
