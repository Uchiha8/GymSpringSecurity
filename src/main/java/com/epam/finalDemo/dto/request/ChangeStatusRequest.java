package com.epam.finalDemo.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record ChangeStatusRequest(
        @Column(nullable = false)
        String username,
        @Column(nullable = false)
        Boolean status) {
}
