package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.Role;
import com.epam.finalDemo.domain.Token;
import com.epam.finalDemo.domain.TokenType;
import com.epam.finalDemo.domain.User;
import com.epam.finalDemo.dto.request.AuthenticationRequest;
import com.epam.finalDemo.dto.request.ChangeLoginRequest;
import com.epam.finalDemo.dto.request.RegisterRequest;
import com.epam.finalDemo.dto.response.AuthenticationResponse;
import com.epam.finalDemo.dto.response.RegistrationResponse;
import com.epam.finalDemo.repository.TokenRepository;
import com.epam.finalDemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public static String password;
    private final TokenRepository tokenRepository;

    public User register(User request) {
        String username = usernameGenerator(request.getFirstName(), request.getLastName());
        password = passwordGenerator();
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(username)
                .password(passwordEncoder.encode(password))
                .isActive(request.getIsActive())
                .role(request.getRole())
                .build();
        return userRepository.save(user);
    }

    public AuthenticationResponse changePassword(ChangeLoginRequest request) {
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new RuntimeException("User not found"));
        if(user!=null){
            user.setPassword(passwordEncoder.encode(request.newPassword()));
            userRepository.save(user);
        }
        return authenticate(new AuthenticationRequest(request.username(), request.newPassword()));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        revokeAllTokens(user);
        savedUserTokens(jwtToken, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void savedUserTokens(String jwtToken, User savedUser) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .user(savedUser)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
            tokenRepository.save(token);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public String usernameGenerator(String firstName, String lastName) {
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
        if (existsByUsername(username)) {
            int i = 1;
            while (existsByUsername(username + i)) {
                i++;
            }
            username = username + i;
        }
        return username;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public String passwordGenerator() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append((char) (Math.random() * 26 + 97));
        }
        return password.toString();
    }

}
