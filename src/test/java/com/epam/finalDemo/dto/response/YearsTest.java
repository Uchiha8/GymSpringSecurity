package com.epam.finalDemo.dto.response;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class YearsTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        int year = 2022;
        List<Month> months = List.of(new Month("January", Duration.ofHours(1)));

        // Act
        Years years = new Years(year, months);

        // Assert
        assertEquals(year, years.getYear());
        assertEquals(months, years.getMonths());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Years years = new Years();

        // Assert
        assertEquals(0, years.getYear());
        assertNull(years.getMonths());
    }

    @Test
    void testBuilder() {
        // Arrange
        int year = 2023;
        List<Month> months = List.of(new Month("February", Duration.ofHours(2)));

        // Act
        Years years = Years.builder()
                .year(year)
                .months(months)
                .build();

        // Assert
        assertEquals(year, years.getYear());
        assertEquals(months, years.getMonths());
    }

    @Test
    void testGetterSetter() {
        // Arrange
        int year = 2024;
        List<Month> months = List.of(new Month("March", Duration.ofHours(3)));

        // Act
        Years years = new Years();
        years.setYear(year);
        years.setMonths(months);

        // Assert
        assertEquals(year, years.getYear());
        assertEquals(months, years.getMonths());
    }
}

