package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class TrainerClientDTOTest {
    @Test
    void testRecordEqualityWithBuilder() {
        Date date = new Date();
        // Arrange
        TrainerClientDTO trainer1 = TrainerClientDTO.builder()
                .username("username")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(date)
                .duration(Duration.ofHours(1))
                .actionType(ActionType.CREATE)
                .build();

        TrainerClientDTO trainer2 = TrainerClientDTO.builder()
                .username("username")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(date)
                .duration(Duration.ofHours(1))
                .actionType(ActionType.CREATE)
                .build();

        TrainerClientDTO differentTrainer = TrainerClientDTO.builder()
                .username("otherUsername")
                .firstName("Jane")
                .lastName("Smith")
                .isActive(false)
                .dateTime(date)
                .duration(Duration.ofMinutes(30))
                .actionType(ActionType.DELETE)
                .build();

        // Act & Assert
        assertEquals(trainer1, trainer2);
        assertNotEquals(trainer1, differentTrainer);
    }
    @Test
    void testToString() {
        // Arrange
        TrainerClientDTO trainer = new TrainerClientDTO("username", "John", "Doe", true, new Date(), Duration.ofHours(1), ActionType.CREATE);

        // Act & Assert
        assertEquals("TrainerClientDTO[username=username, firstName=John, lastName=Doe, isActive=true, dateTime=" +
                trainer.dateTime() + ", duration=PT1H, actionType=CREATE]", trainer.toString());
    }
}
