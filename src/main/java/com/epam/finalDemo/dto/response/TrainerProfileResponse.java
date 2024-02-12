package com.epam.finalDemo.dto.response;

import java.util.List;

public record TrainerProfileResponse(
        String fistName,
        String lastName,
        String trainingType,
        Boolean isActive,
        List<TraineeList> trainees
) {
}
