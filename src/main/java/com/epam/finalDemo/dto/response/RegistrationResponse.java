package com.epam.finalDemo.dto.response;

import lombok.Builder;

@Builder
public record RegistrationResponse(
        String username,
        String password,
        String token) {
}
