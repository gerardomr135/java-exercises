package com.fireal99.Collections.Set;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeSetExamplesTest {
    @Test
    public void createTreeSetExample() {
        Set<String> treeSet = new TreeSet<>();

        treeSet.add("c");
        treeSet.add("b");
        treeSet.add("a");

        assertEquals(Arrays.asList("a", "b", "c"), treeSet.stream().toList());
    }
}
