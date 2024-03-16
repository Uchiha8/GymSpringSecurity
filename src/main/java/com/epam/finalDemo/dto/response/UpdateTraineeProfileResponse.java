package com.epam.finalDemo.dto.response;

import lombok.Builder;

import java.util.Date;
import java.util.List;
@Builder
public record UpdateTraineeProfileResponse(
        String username,
        String firstName,
        String lastName,
        Date dateOfBirth,
        String address,
        Boolean isActive,
        List<TrainerList> trainers) {
}
