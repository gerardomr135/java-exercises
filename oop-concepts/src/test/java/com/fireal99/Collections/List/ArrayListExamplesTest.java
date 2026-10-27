package com.fireal99.Collections.List;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayListExamplesTest {
    @Test
    public void creatingArrayListTest() {
        List<String> list = new ArrayList<>();
        assertTrue(list.isEmpty());

        // Initial Capacity
        list = new ArrayList<>(20);
        assertEquals(0, list.size());

        // Accepting Collection
        Collection<Integer> numbers
                = IntStream.range(0, 10).boxed().collect(toSet());

        List<Integer> listInteger = new ArrayList<>(numbers);
        assertEquals(10, listInteger.size());
        assertTrue(numbers.containsAll(listInteger));

        Collection<String> unmodifiableColl = Collections.unmodifiableCollection(list);
        List<String> unmodifiableList = List.of("a", "b", "c");

        assertThrows(UnsupportedOperationException.class, ()-> {unmodifiableColl.add("e"); });
        assertThrows(UnsupportedOperationException.class, ()-> {unmodifiableList.add("e"); });

        // Add collection or batch
        List<Long> listL = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        LongStream.range(4, 10).boxed()
                .collect(collectingAndThen(toCollection(ArrayList::new), ys -> listL.addAll(0, ys)));
        assertEquals(Arrays.asList(4L, 5L, 6L, 7L, 8L, 9L, 1L, 2L, 3L), listL);

    }

    @Test
    public void searchUnsortedListTest() {
        List<String> list = LongStream.range(0, 16)
                .boxed()
                .map(Long::toHexString)
                .collect(toCollection(ArrayList::new));
        List<String> stringsToSearch = new ArrayList<>(list);
        stringsToSearch.addAll(list);

        Set<String> matchingStrings = new HashSet<>(Arrays.asList("a", "c", "9"));

        List<String> result = stringsToSearch
                .stream()
                .filter(matchingStrings::contains)
                .collect(toCollection(ArrayList::new));

        assertEquals(6, result.size());
    }
}
