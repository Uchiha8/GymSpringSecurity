package com.epam.finalDemo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingTypeTest {

    @Test
    public void testTrainingTypeBuilder() {
        // Given
        Long id = 1L;
        String name = "Java Training";

        // When
        TrainingType trainingType = TrainingType.builder()
                .id(id)
                .name(name)
                .build();

        // Then
        assertNotNull(trainingType);
        assertEquals(id, trainingType.getId());
        assertEquals(name, trainingType.getName());
    }
    @Test
    public void testNoArgsConstructor() {
        TrainingType trainingType = new TrainingType();
        assertNotNull(trainingType);
    }

    @Test
    public void testAllArgsConstructor() {
        TrainingType trainingType = new TrainingType(1L, "Java Programming");

        assertNotNull(trainingType);
        assertEquals(1L, trainingType.getId());
        assertEquals("Java Programming", trainingType.getName());
    }

    @Test
    public void testSetterAndGetterMethods() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName("Java Programming");

        assertEquals(1L, trainingType.getId());
        assertEquals("Java Programming", trainingType.getName());
    }
}
