package com.epam.finalDemo.dto.response;

import java.time.Duration;
import java.util.Date;

public record TrainerTrainingsResponse(
        String trainingName,
        Date trainingDate,
        String trainingType,
        Duration duration,
        String traineeName) {
}
