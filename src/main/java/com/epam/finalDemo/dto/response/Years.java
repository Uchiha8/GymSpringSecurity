package com.epam.finalDemo.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Years {
    private int year;
    private List<Month> months;
}
