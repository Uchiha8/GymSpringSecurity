package com.epam.finalDemo.dto.response;


import lombok.*;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Month {
    private String month;
    private Duration summaryDuration;
}
