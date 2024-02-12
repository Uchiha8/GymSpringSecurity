package com.epam.finalDemo.dto.request;

public record AuthenticationRequest(
        String username,
        String password
) {
}
