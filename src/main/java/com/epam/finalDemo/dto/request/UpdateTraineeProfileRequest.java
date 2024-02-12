package com.epam.finalDemo.dto.request;

import jakarta.persistence.Column;

import java.util.Date;

public record UpdateTraineeProfileRequest(
        @Column(nullable = false)
        String username,
        @Column(nullable = false)
        String firstName,
        @Column(nullable = false)
        String lastName,
        Date dateOfBirth,
        String address,
        @Column(nullable = false)
        Boolean isActive) {
}
