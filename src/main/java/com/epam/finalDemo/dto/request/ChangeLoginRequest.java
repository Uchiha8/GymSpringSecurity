package com.epam.finalDemo.dto.request;

import jakarta.persistence.Column;

public record ChangeLoginRequest(
        @Column(nullable = false)
        String username,
        @Column(nullable = false)
        String oldPassword,
        @Column(nullable = false)
        String newPassword) {
}
