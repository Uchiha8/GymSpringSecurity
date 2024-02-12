package com.epam.finalDemo.dto.response;

import java.util.List;

public record UpdateTrainerProfileResponse(
        String username,
        String firstName,
        String lastName,
        String trainingType,
        Boolean isActive,
        List<TraineeList> trainees) {
}
