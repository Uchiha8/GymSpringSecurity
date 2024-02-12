package com.epam.finalDemo.dto.response;

import java.util.Date;
import java.util.List;

public record UpdateTraineeProfileResponse(
        String username,
        String firstName,
        String lastName,
        Date dateOfBirth,
        String address,
        Boolean isActive,
        List<TrainerList> trainers) {
}
