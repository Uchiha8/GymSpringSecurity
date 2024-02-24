package com.epam.finalDemo.dto.response;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Month {
    @Enumerated(EnumType.STRING)
    private EnumMonth month;
    private Duration summaryDuration;
}
