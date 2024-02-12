package com.epam.finalDemo.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token) {
}
