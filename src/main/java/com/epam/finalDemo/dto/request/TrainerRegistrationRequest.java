package com.epam.finalDemo.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record TrainerRegistrationRequest(
        @Column(nullable = false)
        String firstName,
        @Column(nullable = false)
        String lastName,
        @Column(nullable = false)
        String trainingType) {
}
