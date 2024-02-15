package com.epam.finalDemo.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    private static final String USERNAME = "testUser";
    private static final String SECRET_KEY = "50OKcVS3C+l5uacdS9BOePB4MYNiIqAm+/oy6AZbJA3tdNcCZWxhxuSYKXfqy4e3";

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private Claims mockClaims;

    @Mock
    private UserDetails mockUserDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExtractClaim() {
        // Given
        String token = jwtService.generateToken(createUserDetails());
        when(mockClaims.getSubject()).thenReturn(USERNAME);

        // When
        Claims actualClaims = jwtService.extractClaim(token, claims -> mockClaims);

        // Then
        assertNotNull(actualClaims);
        assertEquals(USERNAME, actualClaims.getSubject());
    }

    @Test
    public void testGenerateToken() {
        // Given
        UserDetails userDetails = createUserDetails();
        when(mockUserDetails.getUsername()).thenReturn(USERNAME);

        // When
        String token = jwtService.generateToken(userDetails);

        // Then
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testGenerateTokenWithExtraClaims() {
        // Given
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("key", "value");
        UserDetails userDetails = createUserDetails();
        when(mockUserDetails.getUsername()).thenReturn(USERNAME);

        // When
        String token = jwtService.generateToken(extraClaims, userDetails);

        // Then
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testIsTokenValid() {
        // Given
        UserDetails userDetails = createUserDetails();
        when(mockUserDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(userDetails);

        // When
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Then
        assertTrue(isValid);
    }


    @Test
    public void testIsTokenExpired() {
        // Given
        UserDetails userDetails = createUserDetails();
        when(mockUserDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(userDetails);

        // When
        boolean isExpired = jwtService.isTokenExpired(token);

        // Then
        assertFalse(isExpired);
    }

    @Test
    public void testExtractExpiration() {
        // Given
        String token = jwtService.generateToken(createUserDetails());

        // When
        Date expirationDate = jwtService.extractExpiration(token);

        // Then
        assertNotNull(expirationDate);
    }

    @Test
    public void testExtractAllClaims() {
        // Given
        String token = jwtService.generateToken(createUserDetails());
        when(mockClaims.getSubject()).thenReturn(USERNAME);

        // When
        Claims claims = jwtService.extractAllClaims(token);

        // Then
        assertNotNull(claims);
    }

    @Test
    public void testGetSigningKey() {
        // When
        byte[] keyBytes = jwtService.getSigningKey().getEncoded();

        // Then
        assertNotNull(keyBytes);
    }

    private UserDetails createUserDetails() {
        return mockUserDetails;
    }
}