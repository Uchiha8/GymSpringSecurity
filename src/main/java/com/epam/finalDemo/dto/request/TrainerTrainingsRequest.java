package com.epam.finalDemo.dto.request;

import java.util.Date;

public record TrainerTrainingsRequest(
        String username,
        Date periodFrom,
        Date periodTo,
        String traineeName) {
}
