package com.fireal99.Strings;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StringsTest {
    @Test
    public void test() {
        String x = "xd";
        String y = "xd";
        assertSame(x, y);

        String z = new String("xd");
        assertNotSame(x, z);

        String newX = x.toUpperCase();
        assertNotSame(x, newX);
    }

    @Test
    public void testStringBuffer() throws InterruptedException {
        StringBuffer x = new StringBuffer("Text Added via " + Thread.currentThread().getName() + "\n");

        Thread t1 = new Thread(() -> {
            x.append("Text Added via ")
                    .append(Thread.currentThread().getName())
                    .append("\n");
        });
        Thread t2 = new Thread(() -> {
            x.append("Text Added via ")
                    .append(Thread.currentThread().getName())
                    .append("\n");
        });


        t2.start();
        t1.start();

        Thread.sleep(1000);

        String[] lines = x.toString().split("\n");

        assertEquals(3, lines.length);

        System.out.println(Arrays.toString(lines));
    }
}
