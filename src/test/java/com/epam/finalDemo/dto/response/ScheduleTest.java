package com.epam.finalDemo.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ScheduleTest {

    @Test
    void testEquality() {
        // Arrange
        Schedule schedule1 = Schedule.builder()
                .id("1")
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .status(true)
                .years(Collections.emptyList())
                .build();

        Schedule schedule2 = Schedule.builder()
                .id("1")
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .status(true)
                .years(Collections.emptyList())
                .build();

        Schedule differentSchedule = Schedule.builder()
                .id("3")
                .username("jane.smith")
                .firstName("Jane")
                .lastName("Smith")
                .status(false)
                .years(List.of(new Years(2023, Collections.emptyList())))
                .build();

        // Act & Assert
        assertEquals(schedule1, schedule2);
        assertNotEquals(schedule1, differentSchedule);
    }

    @Test
    void testToString() {
        // Arrange
        Schedule schedule = Schedule.builder()
                .id("1")
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .status(true)
                .years(Collections.emptyList())
                .build();

        // Act
        String toStringResult = schedule.toString();

        // Assert
        assertEquals("Schedule[id=1, username=john.doe, firstName=John, lastName=Doe, status=true, years=[]]", toStringResult);
    }
}

