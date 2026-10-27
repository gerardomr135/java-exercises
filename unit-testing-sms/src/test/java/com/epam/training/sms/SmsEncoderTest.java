package com.epam.training.sms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmsEncoderTest {
    private static SmsEncoder smsEncoder;
    @BeforeAll
    static void beforeAll() {
        smsEncoder = new SmsEncoder();
    }

    @DisplayName("Tests encode with a single character")
    @Test
    public void encodeSingleKey() {
        var res = smsEncoder.encode("a");

        assertEquals("2", res);
    }

    @DisplayName("Tests encode with two characters")
    @Test
    public void encodeDoubleKey() {
        var res = smsEncoder.encode("go");
        assertEquals("4666",res);
    }

    @DisplayName("Tests encode with multiple characters")
    @Test
    public void encodeMultipleKey() {
        var res = smsEncoder.encode("hello world");
        assertEquals("4433555 555666096667775553", res);
    }

    @DisplayName("Tests encode with empty text")
    @Test
    public void encodeEmptyInput() {
        var res = smsEncoder.encode("");
        assertEquals("", res);
    }

    @DisplayName("Tests encode with invalid characters")
    @Test
    public void encodeInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> smsEncoder.encode("!#"));
    }

    @DisplayName("Tests decode 4666")
    @Test
    public void decodeGo() {
        var res =  smsEncoder.decode("4666");
        assertEquals("go",res);
    }

    @DisplayName("Tests decode exception")
    @Test
    public void decodeInvalidInput() {
        assertThrows(IllegalArgumentException.class, ()-> smsEncoder.decode("text"));
    }
}
