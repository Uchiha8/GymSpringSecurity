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
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

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

        // Mocking Authorization header with an invalid JWT token
        when(request.getHeader("Authorization")).thenReturn("InvalidJwtToken");

        // When
        logoutHandlerService.logout(request, response, authentication);

        // Then
        // Verify that findByToken method was not called for an invalid token
        verify(tokenRepository, never()).findByToken(any());

        // Verify that save method was not called on the tokenRepository
        verify(tokenRepository, never()).save(any());
    }

    private Optional<Token> stubToken() {
        // Implement a stub Token for testing purposes
        // Adjust this method based on your Token class structure
        // For example:
        return Optional.of(new Token(1L, "validJwtToken", TokenType.BEARER, false, false, new User()));
    }
}
