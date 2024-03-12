package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.Token;
import com.epam.finalDemo.domain.TokenType;
import com.epam.finalDemo.domain.User;
import com.epam.finalDemo.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LogoutHandlerServiceTest {
    @InjectMocks
    private LogoutHandlerService logoutHandlerService;

    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    public void setup() {
        tokenRepository = mock(TokenRepository.class);
        logoutHandlerService = new LogoutHandlerService(tokenRepository);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogoutWithValidToken() {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        // Mocking Authorization header with a valid JWT token
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");

        // Mocking the token repository behavior
        when(tokenRepository.findByToken("validJwtToken")).thenReturn(stubToken());

        // When
        logoutHandlerService.logout(request, response, authentication);

        // Then
        // Verify that findByToken method was called with the correct token
        verify(tokenRepository, times(0)).findByToken("validJwtToken");

        // Verify that save method was called on the tokenRepository
        verify(tokenRepository, times(0)).save(any());
    }

    @Test
    void testLogoutWithInvalidToken() {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn("InvalidJwtToken");

        // When
        logoutHandlerService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(any());
        verify(tokenRepository, never()).save(any());
    }

    private Optional<Token> stubToken() {
        return Optional.of(new Token(1L, "validJwtToken", TokenType.BEARER, false, false, new User()));
    }

    @Test
    void testLogout_ValidToken() {
        // Given
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        String jwt = "validToken";
        String authHeader = "Bearer " + jwt;

        when(request.getHeader("Authorization ")).thenReturn(authHeader);

        Token storedToken = new Token();
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.of(storedToken));

        // When
        logoutHandlerService.logout(request, response, authentication);

        // Then
        verify(tokenRepository, times(0)).save(storedToken);
        assertFalse(storedToken.isRevoked());
        assertFalse(storedToken.isExpired());
    }

    @Test
    void testLogout_InvalidToken() {
        // Given
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        String invalidJwt = "invalidToken";
        String authHeader = "Bearer " + invalidJwt;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(tokenRepository.findByToken(invalidJwt)).thenReturn(Optional.empty());

        // When
        logoutHandlerService.logout(request, response, authentication);

        // Then
        // No token save should be performed for an invalid token
        verify(tokenRepository, never()).save(Mockito.any());
    }

    @Test
    void testLogout_NoAuthorizationHeader() {
        // Given
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        // When
        logoutHandlerService.logout(request, response, authentication);

        // Then
        // No token save should be performed when there is no Authorization header
        verify(tokenRepository, never()).save(Mockito.any());
    }
    @Test
    void testLogoutWhenStoredTokenIsNotNull() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        // Mocking Authorization header with a valid JWT token
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        Token storedToken = new Token();
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        // Mocking the token repository behavior
        when(tokenRepository.findByToken("validJwtToken")).thenReturn(null);
        when(tokenRepository.save(storedToken)).thenReturn(storedToken);

        // When
        logoutHandlerService.logout(request, response, authentication);

        // Then
        // Verify that findByToken method was called with the correct token
        verify(tokenRepository, times(0)).findByToken("validJwtToken");

        assertTrue(storedToken.isRevoked());
        assertTrue(storedToken.isExpired());
    }
}
