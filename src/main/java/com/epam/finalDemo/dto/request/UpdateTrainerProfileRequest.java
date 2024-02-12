package com.epam.finalDemo.dto.request;

import jakarta.persistence.Column;

public record UpdateTrainerProfileRequest(
        @Column(nullable = false)
        String username,
        @Column(nullable = false)
        String firstName,
        @Column(nullable = false)
        String lastName,
        @Column(nullable = false)
        String trainingType,
        @Column(nullable = false)
        Boolean isActive) {
}
