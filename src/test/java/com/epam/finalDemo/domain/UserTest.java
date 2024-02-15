package com.epam.finalDemo.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class UserTest {

    @Test
    public void testUserBuilder() {
        // Given
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String username = "john.doe";
        String password = "password123";
        Boolean isActive = true;
        Role role = Role.ROLE_USER;
        List<Token> tokens = new ArrayList<>(); // You may need to create Token objects with appropriate data.

        // When
        User user = User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .isActive(isActive)
                .role(role)
                .tokens(tokens)
                .build();

        // Then
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(isActive, user.getIsActive());
        assertEquals(role, user.getRole());
        assertEquals(tokens, user.getTokens());
    }

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testAllArgsConstructor() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(Mockito.mock(Token.class));

        User user = new User(1L, "John", "Doe", "john.doe", "password", true, Role.ROLE_USER, tokens);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.getIsActive());
        assertEquals(Role.ROLE_USER, user.getRole());
        assertEquals(tokens, user.getTokens());
    }

    @Test
    public void testSetterAndGetterMethods() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("john.doe");
        user.setPassword("password");
        user.setIsActive(true);
        user.setRole(Role.ROLE_USER);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.getIsActive());
        assertEquals(Role.ROLE_USER, user.getRole());
    }

    @Test
    public void testTokensAssociation() {
        User user = new User();
        List<Token> tokens = new ArrayList<>();
        tokens.add(Mockito.mock(Token.class));
        user.setTokens(tokens);

        assertEquals(tokens, user.getTokens());
    }

    @Test
    public void testGetAuthorities() {
        // Given
        Role role = Role.ROLE_USER;
        User user = User.builder().role(role).build();

        // When
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Then
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(role.name())));
    }

    @Test
    public void testIsAccountNonExpired() {
        // Given
        User user = User.builder().build();

        // When
        boolean isAccountNonExpired = user.isAccountNonExpired();

        // Then
        assertTrue(isAccountNonExpired);
    }

    @Test
    public void testIsAccountNonLocked() {
        // Given
        User user = User.builder().build();

        // When
        boolean isAccountNonLocked = user.isAccountNonLocked();

        // Then
        assertTrue(isAccountNonLocked);
    }

    @Test
    public void testIsCredentialsNonExpired() {
        // Given
        User user = User.builder().build();

        // When
        boolean isCredentialsNonExpired = user.isCredentialsNonExpired();

        // Then
        assertTrue(isCredentialsNonExpired);
    }

    @Test
    public void testIsEnabled() {
        // Given
        User user = User.builder().build();

        // When
        boolean isEnabled = user.isEnabled();

        // Then
        assertTrue(isEnabled);
    }
}
