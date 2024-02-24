package com.epam.finalDemo.dto.request;

import lombok.Builder;

import java.time.Duration;
import java.util.Date;

@Builder
public record TrainerClientDTO(
        String username,
        String firstName,
        String lastName,
        Boolean isActive,
        Date dateTime,
        Duration duration,
        ActionType actionType
) {
}
