package com.epam.training.unclean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        
        Application app = new Application();

        System.out.println(app.filter(List.of(1, 2, 1, 5, 3, 2, 2, 2, 2, 4, 2)));
        System.out.println(app.filter(List.of(1, 2, 1, 2, 1, 2, 1, 2, 3)));
        System.out.println(app.filter(List.of(1, 2, 1, 2, 1, 2, 1, 2)));
        System.out.println(app.filter(List.of(0, 0, 0, 0)));
        System.out.println(app.filter(List.of(1)));
        System.out.println(app.filter(List.of()));
        System.out.println(app.filter(null));


    }

    // This code checks the even numbers in the array
    public static List<Integer> filter(List<? extends Integer> inputList) {
        if (inputList == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        HashMap<Integer, Integer> counter = new LinkedHashMap<>();
        for (Integer i : inputList) {
            counter.put(i, counter.getOrDefault(i, 0) + 1);
        }

        List<Integer> result = new ArrayList<>();
        for (Integer i : counter.keySet()) {
            if (counter.get(i) == 1) {
                result.add(i);
            }
        }

        return result;
    }
}
