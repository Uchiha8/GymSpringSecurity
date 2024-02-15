package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.Role;
import com.epam.finalDemo.domain.Token;
import com.epam.finalDemo.domain.TokenType;
import com.epam.finalDemo.domain.User;
import com.epam.finalDemo.dto.request.AuthenticationRequest;
import com.epam.finalDemo.dto.request.ChangeLoginRequest;
import com.epam.finalDemo.dto.response.AuthenticationResponse;
import com.epam.finalDemo.repository.TokenRepository;
import com.epam.finalDemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        User request = new User(1L, "John", "Doe", null, null, true, Role.ROLE_USER, List.of());

        // When
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(request);
        User result = userService.register(request);

        // Then
        verify(userRepository, times(1)).save(any());
        assertEquals(request.getFirstName(), result.getFirstName());
    }

    @Test
    void testChangePassword() {
        // Given
        ChangeLoginRequest changeLoginRequest = new ChangeLoginRequest("username", "oldPassword", "newPassword");
        User user = new User(1L, "username", "John", "Doe", "oldPassword", true, Role.ROLE_USER, null);
        List<Token> tokens = List.of(new Token(1L, "token", null, false, false, user));

        // When
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedNewPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(tokenRepository.findAllValidTokenByUser(any())).thenReturn(tokens);
        when(tokenRepository.save(any())).thenReturn(new Token(1L, "token", null, false, false, user));
        when(tokenRepository.saveAll(any())).thenReturn(tokens);
        AuthenticationResponse result = userService.changePassword(changeLoginRequest);

        // Then
        verify(userRepository, times(1)).save(any());
        assertEquals("jwtToken", result.token());
    }

    @Test
    void testAuthenticate() {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        User user = new User(1L, "username", "John", "Doe", "encodedPassword", true, Role.ROLE_USER, null);
        List<Token> tokens = List.of(new Token(1L, "token", null, false, false, user));
        // When
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(tokenRepository.findAllValidTokenByUser(any())).thenReturn(tokens);
        when(tokenRepository.save(any())).thenReturn(new Token(1L, "token", null, false, false, user));
        when(tokenRepository.saveAll(any())).thenReturn(tokens);

        AuthenticationResponse result = userService.authenticate(authenticationRequest);

        // Then
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByUsername("username");
        verify(jwtService, times(1)).generateToken(user);
        assertEquals("jwtToken", result.token());
    }

    @Test
    void testSavedUserTokens() {
        String jwtToken = "jwtToken";
        User savedUser = new User(1L, "username", "John", "Doe", "encodedPassword", true, Role.ROLE_USER, null);
        Token token = new Token(null, jwtToken, TokenType.BEARER, false, false, savedUser);
        when(tokenRepository.save(token)).thenReturn(token);
        userService.savedUserTokens(jwtToken, savedUser);
        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    void testRevokeAllTokens() {
        User user = new User(1L, "username", "John", "Doe", "encodedPassword", true, Role.ROLE_USER, null);
        List<Token> validUserTokens = List.of(new Token(1L, "token", null, false, false, user));
        when(tokenRepository.findAllValidTokenByUser(user.getId())).thenReturn(validUserTokens);
        when(tokenRepository.save(any())).thenReturn(new Token(1L, "token", null, true, true, user));
        userService.revokeAllTokens(user);
        verify(tokenRepository, times(1)).findAllValidTokenByUser(user.getId());
        verify(tokenRepository, times(1)).save(any());
    }

    @Test
    void testUsernameGenerator() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        when(userRepository.existsByUsername("john.doe")).thenReturn(false);
        when(userRepository.existsByUsername("john.doe1")).thenReturn(false);
        when(userRepository.existsByUsername("john.doe2")).thenReturn(true);
        String username = userService.usernameGenerator(firstName, lastName);
        assertNotNull(username);
    }

    @Test
    void testExistsByUsername() {
        // Given
        String username = "john.doe";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // When
        boolean result = userService.existsByUsername(username);

        // Then
        verify(userRepository, times(1)).existsByUsername(username);
        assertTrue(result);
    }

    @Test
    void testPasswordGenerator() {
        // Given
        String password = userService.passwordGenerator();
        // Then
        assertNotNull(password);
    }
}
