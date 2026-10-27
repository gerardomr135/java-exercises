package com.epam.training.food;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    public void checkAValidISBN()
    {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("0140449116");
        assertTrue(result);

        result = validateISBN.checkISBN("0140177396");
        assertTrue(result);
    }

    @Test
    public void testNineISBN9Digits() {
        ValidateISBN validateISBN = new ValidateISBN();
        assertThrows(NumberFormatException.class, () -> validateISBN.checkISBN("014044911"));
    }

    @Test
    public void testNineISBNNonDigits() {
        ValidateISBN validateISBN = new ValidateISBN();
        assertThrows(NumberFormatException.class, () -> validateISBN.checkISBN("helloworld"));
    }

    @Test
    public void testISBNEndingInAnX() {
        ValidateISBN validateISBN = new ValidateISBN();
        assertTrue(validateISBN.checkISBN("012000030X"));
    }
}
