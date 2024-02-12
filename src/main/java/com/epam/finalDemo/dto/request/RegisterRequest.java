package com.epam.finalDemo.dto.request;

public record RegisterRequest(
        String firstName,
        String lastName,
        String username,
        String password,
        Boolean isActive,
        String role) {
}
