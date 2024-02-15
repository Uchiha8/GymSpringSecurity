package com.epam.finalDemo.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
public class TrainerTest {

    @Test
    public void testTrainerBuilder() {
        // Given
        Long id = 1L;
        TrainingType trainingType = new TrainingType(); // You may need to create a TrainingType object with appropriate data.
        User user = new User(); // You may need to create a User object with appropriate data.
        List<Training> trainings = new ArrayList<>(); // You may need to create Training objects with appropriate data.

        // When
        Trainer trainer = Trainer.builder()
                .id(id)
                .trainingType(trainingType)
                .user(user)
                .trainings(trainings)
                .build();

        // Then
        assertNotNull(trainer);
        assertEquals(id, trainer.getId());
        assertEquals(trainingType, trainer.getTrainingType());
        assertEquals(user, trainer.getUser());
        assertEquals(trainings, trainer.getTrainings());
    }
    @Test
    public void testNoArgsConstructor() {
        Trainer trainer = new Trainer();
        assertNotNull(trainer);
    }

    @Test
    public void testAllArgsConstructor() {
        TrainingType trainingType = Mockito.mock(TrainingType.class);
        User user = Mockito.mock(User.class);
        List<Training> trainings = new ArrayList<>();
        trainings.add(Mockito.mock(Training.class));

        Trainer trainer = new Trainer(1L, trainingType, user, trainings);

        assertNotNull(trainer);
        assertEquals(1L, trainer.getId());
        assertEquals(trainingType, trainer.getTrainingType());
        assertEquals(user, trainer.getUser());
        assertEquals(trainings, trainer.getTrainings());
    }

    @Test
    public void testSetterAndGetterMethods() {
        Trainer trainer = new Trainer();
        TrainingType trainingType = Mockito.mock(TrainingType.class);
        User user = Mockito.mock(User.class);
        List<Training> trainings = new ArrayList<>();
        trainings.add(Mockito.mock(Training.class));

        trainer.setId(1L);
        trainer.setTrainingType(trainingType);
        trainer.setUser(user);
        trainer.setTrainings(trainings);

        assertEquals(1L, trainer.getId());
        assertEquals(trainingType, trainer.getTrainingType());
        assertEquals(user, trainer.getUser());
        assertEquals(trainings, trainer.getTrainings());
    }

    @Test
    public void testTrainingTypeAssociation() {
        Trainer trainer = new Trainer();
        TrainingType trainingType = Mockito.mock(TrainingType.class);
        trainer.setTrainingType(trainingType);

        assertEquals(trainingType, trainer.getTrainingType());
    }

    @Test
    public void testUserAssociation() {
        Trainer trainer = new Trainer();
        User user = Mockito.mock(User.class);
        trainer.setUser(user);

        assertEquals(user, trainer.getUser());
    }

    @Test
    public void testTrainingAssociation() {
        Trainer trainer = new Trainer();
        List<Training> trainings = new ArrayList<>();
        trainings.add(Mockito.mock(Training.class));
        trainer.setTrainings(trainings);

        assertEquals(trainings, trainer.getTrainings());
    }
}
