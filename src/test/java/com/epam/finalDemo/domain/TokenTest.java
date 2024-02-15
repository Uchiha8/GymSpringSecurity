package com.epam.finalDemo.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
public class TokenTest {

    @Test
    public void testTokenBuilder() {
        // Given
        Long id = 1L;
        String tokenValue = "testToken";
        TokenType tokenType = TokenType.BEARER;
        boolean expired = false;
        boolean revoked = false;
        User user = new User(); // You may need to create a User object with appropriate data.

        // When
        Token token = Token.builder()
                .id(id)
                .token(tokenValue)
                .tokenType(tokenType)
                .expired(expired)
                .revoked(revoked)
                .user(user)
                .build();

        // Then
        assertNotNull(token);
        assertEquals(id, token.getId());
        assertEquals(tokenValue, token.getToken());
        assertEquals(tokenType, token.getTokenType());
        assertEquals(expired, token.isExpired());
        assertEquals(revoked, token.isRevoked());
        assertEquals(user, token.getUser());
    }
    @Test
    public void testNoArgsConstructor() {
        Token token = new Token();
        assertNotNull(token);
    }

    @Test
    public void testAllArgsConstructor() {
        User user = Mockito.mock(User.class);
        Token token = new Token(1L, "someToken", TokenType.BEARER, false, false, user);

        assertNotNull(token);
        assertEquals(1L, token.getId());
        assertEquals("someToken", token.getToken());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertFalse(token.isExpired());
        assertFalse(token.isRevoked());
        assertEquals(user, token.getUser());
    }

    @Test
    public void testSetterAndGetterMethods() {
        Token token = new Token();
        token.setId(1L);
        token.setToken("someToken");
        token.setTokenType(TokenType.BEARER);
        token.setExpired(true);
        token.setRevoked(true);

        assertEquals(1L, token.getId());
        assertEquals("someToken", token.getToken());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
    }

    @Test
    public void testUserAssociation() {
        Token token = new Token();
        User user = Mockito.mock(User.class);
        token.setUser(user);

        assertEquals(user, token.getUser());
    }
}
