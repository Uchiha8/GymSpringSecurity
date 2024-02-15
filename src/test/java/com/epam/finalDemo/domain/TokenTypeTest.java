package com.epam.finalDemo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTypeTest {
    @Test
    public void testEnumValues() {
        TokenType bearerToken = TokenType.BEARER;

        assertEquals("BEARER", bearerToken.name());
    }

    @Test
    public void testEnumToString() {
        TokenType bearerToken = TokenType.BEARER;

        assertEquals("BEARER", bearerToken.toString());
    }
}
