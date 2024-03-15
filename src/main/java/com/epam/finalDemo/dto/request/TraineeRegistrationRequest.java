package com.epam.finalDemo.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;

import java.util.Date;
@Builder
public record TraineeRegistrationRequest(
        @Column(nullable = false)
        String firstName,
        @Column(nullable = false)
        String lastName,
        Date dateOfBirth,
        String address) {
}
