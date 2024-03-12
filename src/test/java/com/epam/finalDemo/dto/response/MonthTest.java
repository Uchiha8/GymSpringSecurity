package com.epam.finalDemo.dto.response;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MonthTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String month = "January";
        Duration summaryDuration = Duration.ofHours(10);

        // Act
        Month monthObject = new Month(month, summaryDuration);

        // Assert
        assertEquals(month, monthObject.getMonth());
        assertEquals(summaryDuration, monthObject.getSummaryDuration());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Month monthObject = new Month();

        // Assert
        assertNull(monthObject.getMonth());
        assertNull(monthObject.getSummaryDuration());
    }

    @Test
    void testBuilder() {
        // Arrange
        String month = "February";
        Duration summaryDuration = Duration.ofHours(5);

        // Act
        Month monthObject = Month.builder()
                .month(month)
                .summaryDuration(summaryDuration)
                .build();

        // Assert
        assertEquals(month, monthObject.getMonth());
        assertEquals(summaryDuration, monthObject.getSummaryDuration());
    }

    @Test
    void testGetterSetter() {
        // Arrange
        String month = "March";
        Duration summaryDuration = Duration.ofMinutes(30);

        // Act
        Month monthObject = new Month();
        monthObject.setMonth(month);
        monthObject.setSummaryDuration(summaryDuration);

        // Assert
        assertEquals(month, monthObject.getMonth());
        assertEquals(summaryDuration, monthObject.getSummaryDuration());
    }
}

