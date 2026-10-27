package com.fireal99.Collections.List;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// LinkedList is a doubly-linked list implementation of the List and Deque interfaces.
// It implements all optional list operations and permits all elements (including null).
public class LinkedListExamples {
    // A LinkedList stores its data as a list of elements, and every element is linked to its previous and next element.
    // In this case, the search operation for an item has execution time equal to O(n).

    // The insertion, addition and removal operations of an item are faster in a LinkedList because there is no need to
    // resize an array or update the index when an element is added to some arbitrary position inside the collection,
    // only references in surrounding elements will change.

    // A LinkedList consumes more memory than an ArrayList because of every node in a LinkedList stores two references,
    // one for its previous element and one for its next element, whereas ArrayList holds only data and its index.
    Deque<String> dequeElements = new LinkedList<>();
    List<String> listElements = new LinkedList<>();

    // An ArrayList is an index-based data structure backed by an Array.
    // It provides random access to its elements with a performance equal to O(1).
    List<String> arrayElements = new ArrayList<>();

    // Go to tests...
}

