package com.epam.finalDemo.dto.request;

import jakarta.persistence.Column;

import java.util.Date;

public record TraineeTrainingsRequest(
        @Column(nullable = false)
        String username,
        Date periodFrom,
        Date periodTo,
        String trainerName,
        String trainingType) {
}
