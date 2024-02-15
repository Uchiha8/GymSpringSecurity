package com.epam.finalDemo.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.Date;
public class TrainingTest {

    @Test
    public void testTrainingBuilder() {
        // Given
        Long id = 1L;
        Trainee trainee = new Trainee(); // You may need to create a Trainee object with appropriate data.
        Trainer trainer = new Trainer(); // You may need to create a Trainer object with appropriate data.
        String trainingName = "Java Programming";
        TrainingType trainingType = new TrainingType(); // You may need to create a TrainingType object with appropriate data.
        Date trainingDate = new Date();
        Duration duration = Duration.ofHours(2);

        // When
        Training training = Training.builder()
                .id(id)
                .trainee(trainee)
                .trainer(trainer)
                .trainingName(trainingName)
                .trainingType(trainingType)
                .trainingDate(trainingDate)
                .duration(duration)
                .build();

        // Then
        assertNotNull(training);
        assertEquals(id, training.getId());
        assertEquals(trainee, training.getTrainee());
        assertEquals(trainer, training.getTrainer());
        assertEquals(trainingName, training.getTrainingName());
        assertEquals(trainingType, training.getTrainingType());
        assertEquals(trainingDate, training.getTrainingDate());
        assertEquals(duration, training.getDuration());
    }
    @Test
    public void testNoArgsConstructor() {
        Training training = new Training();
        assertNotNull(training);
    }

    @Test
    public void testAllArgsConstructor() {
        Trainee trainee = Mockito.mock(Trainee.class);
        Trainer trainer = Mockito.mock(Trainer.class);
        TrainingType trainingType = Mockito.mock(TrainingType.class);
        Date trainingDate = new Date();
        Duration duration = Duration.ofHours(2);

        Training training = new Training(1L, trainee, trainer, "Java Training", trainingType, trainingDate, duration);

        assertNotNull(training);
        assertEquals(1L, training.getId());
        assertEquals(trainee, training.getTrainee());
        assertEquals(trainer, training.getTrainer());
        assertEquals("Java Training", training.getTrainingName());
        assertEquals(trainingType, training.getTrainingType());
        assertEquals(trainingDate, training.getTrainingDate());
        assertEquals(duration, training.getDuration());
    }

    @Test
    public void testSetterAndGetterMethods() {
        Training training = new Training();
        Trainee trainee = Mockito.mock(Trainee.class);
        Trainer trainer = Mockito.mock(Trainer.class);
        TrainingType trainingType = Mockito.mock(TrainingType.class);
        Date trainingDate = new Date();
        Duration duration = Duration.ofHours(2);

        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName("Java Training");
        training.setTrainingType(trainingType);
        training.setTrainingDate(trainingDate);
        training.setDuration(duration);

        assertEquals(1L, training.getId());
        assertEquals(trainee, training.getTrainee());
        assertEquals(trainer, training.getTrainer());
        assertEquals("Java Training", training.getTrainingName());
        assertEquals(trainingType, training.getTrainingType());
        assertEquals(trainingDate, training.getTrainingDate());
        assertEquals(duration, training.getDuration());
    }

    @Test
    public void testTraineeAssociation() {
        Training training = new Training();
        Trainee trainee = Mockito.mock(Trainee.class);
        training.setTrainee(trainee);

        assertEquals(trainee, training.getTrainee());
    }

    @Test
    public void testTrainerAssociation() {
        Training training = new Training();
        Trainer trainer = Mockito.mock(Trainer.class);
        training.setTrainer(trainer);

        assertEquals(trainer, training.getTrainer());
    }

    @Test
    public void testTrainingTypeAssociation() {
        Training training = new Training();
        TrainingType trainingType = Mockito.mock(TrainingType.class);
        training.setTrainingType(trainingType);

        assertEquals(trainingType, training.getTrainingType());
    }
}
