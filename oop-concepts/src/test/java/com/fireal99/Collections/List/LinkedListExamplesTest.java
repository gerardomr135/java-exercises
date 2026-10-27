package com.fireal99.Collections.List;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListExamplesTest {
    @Test
    public void initializationTest()
    {
        LinkedList<String> list = new LinkedList<>();

        assertTrue(list.isEmpty());

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);


        LinkedList<Integer> list3 = new LinkedList<>(list2);

        assertArrayEquals(list2.toArray(), list3.toArray());


        Deque<Integer> list4 = new LinkedList<>(list2);

        int count = 0;
        while(!list4.isEmpty()) {
            Integer e = list4.poll();
            assertEquals(++count, e);
        }


        assertThrows(NoSuchElementException.class, list4::pop);
    }

    @Test
    public void pushTest() {
        LinkedList<String> list = new LinkedList<>();
        list.push("a");
        list.push("b");
        list.push("c");

        assertArrayEquals(new String[]{"c", "b", "a"}, list.toArray());

        assertEquals(3, list.size());

        list.clear();

        list.add("d");
        list.add("e");
        list.add("f");

        assertArrayEquals(new String[]{"d", "e", "f"}, list.toArray());
    }

    @Test
    public void fromArrayToLinkedList() {
        Integer[] array = {1, 2, 3, 4, 5};

        List<Integer> list = Arrays.asList(array);

        LinkedList<Integer> list2 = new LinkedList<>(list);


        assertArrayEquals(array, list2.toArray());
    }

    @Test
    public void listOperationsTest() {
        // Iterator to list
        Iterator<Integer> iterator = Arrays.asList(1, 2, 3).iterator();

        List<Integer> actualList = new ArrayList<Integer>();
        while (iterator.hasNext()) {
            actualList.add(iterator.next());
        }

        iterator.forEachRemaining(actualList::add);

        assertEquals(Arrays.asList(1, 2, 3), actualList);

        // Get random element
        List<Integer> list = Arrays.asList(1, 2, 3);
        Random rand = new Random();
        var e = list.get(rand.nextInt(list.size()));
        System.out.println(e);
    }
}
