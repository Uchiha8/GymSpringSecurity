package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.*;
import com.epam.finalDemo.dto.request.PostTrainingRequest;
import com.epam.finalDemo.repository.TraineeRepository;
import com.epam.finalDemo.repository.TrainerRepository;
import com.epam.finalDemo.repository.TrainingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.jms.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.ctc.wstx.shaded.msv_core.grammar.Expression.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TrainingServiceTest {
    @InjectMocks
    private TrainingService trainingService;

    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private Queue queue;
    @Mock
    private TrainerRepository trainerRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Given
        PostTrainingRequest request = createTrainingRequest();
        when(trainingRepository.existsByTrainingName(request.trainingName())).thenReturn(false);
        when(traineeRepository.existsByUserUsername(request.traineeUsername())).thenReturn(true);
        when(trainerRepository.existsByUserUsername(request.trainerUsername())).thenReturn(true);

        Trainer trainer = new Trainer(1L, new TrainingType(1L, "Java"), new User(1L, "John", "Doe", "john.doe", "password", false, Role.ROLE_ADMIN, List.of()), List.of());
        when(trainerRepository.findByUserUsername(request.trainerUsername())).thenReturn(java.util.Optional.of(trainer));
        when(traineeRepository.findByUserUsername(request.traineeUsername())).thenReturn(createTrainee());
        // When
        Training result = trainingService.save(request);

        // Then
        verify(trainingRepository, times(0)).existsByTrainingName(request.trainingName());
        verify(traineeRepository, times(1)).existsByUserUsername(request.traineeUsername());
        verify(trainerRepository, times(1)).existsByUserUsername(request.trainerUsername());
        verify(trainerRepository, times(1)).findByUserUsername(request.trainerUsername());
        verify(traineeRepository, times(1)).findByUserUsername(request.traineeUsername());
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void testSaveTrainingNameExists() {
        // Given
        PostTrainingRequest request = createTrainingRequest();
        when(trainingRepository.existsByTrainingName(request.trainingName())).thenReturn(true);

        // When/Then
        assertThrows(RuntimeException.class, () -> trainingService.save(request));

        verify(traineeRepository, times(1)).existsByUserUsername(anyString());
        verify(trainerRepository, times(0)).existsByUserUsername(anyString());
        verify(trainerRepository, times(0)).findByUserUsername(anyString());
        verify(traineeRepository, times(0)).findByUserUsername(anyString());
        verify(trainingRepository, times(0)).save(any(Training.class));
    }

    @Test
    void testSaveTraineeUsernameNotExists() {
        // Given
        PostTrainingRequest request = createTrainingRequest();
        when(trainingRepository.existsByTrainingName(request.trainingName())).thenReturn(false);
        when(traineeRepository.existsByUserUsername(request.traineeUsername())).thenReturn(false);

        // When/Then
        assertThrows(RuntimeException.class, () -> trainingService.save(request));

        verify(trainingRepository, times(0)).existsByTrainingName(request.trainingName());
        verify(trainerRepository, never()).existsByUserUsername(anyString());
        verify(trainerRepository, never()).findByUserUsername(anyString());
        verify(traineeRepository, never()).findByUserUsername(anyString());
        verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void testSaveTrainerUsernameNotExists() {
        // Given
        PostTrainingRequest request = createTrainingRequest();
        when(trainingRepository.existsByTrainingName(request.trainingName())).thenReturn(false);
        when(traineeRepository.existsByUserUsername(request.traineeUsername())).thenReturn(true);
        when(trainerRepository.existsByUserUsername(request.traineeUsername())).thenReturn(false);

        // When/Then
        assertThrows(RuntimeException.class, () -> trainingService.save(request));

        verify(trainerRepository, never()).findByUserUsername(anyString());
        verify(traineeRepository, never()).findByUserUsername(anyString());
        verify(trainingRepository, never()).save(any(Training.class));
    }


    private PostTrainingRequest createTrainingRequest() {
        return new PostTrainingRequest("TrainingName", "TraineeUsername", "TrainerUsername", new Date(), Duration.ofHours(1));
    }

    private java.util.Optional<Trainee> createTrainee() {
        return Optional.of(new Trainee());
    }

    @Test
    void testDeleteByTrainingNameTrainingNotFound() {
        // Arrange
        String trainingName = "NonexistentTraining";
        when(trainingRepository.existsByTrainingName(trainingName)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> trainingService.deleteByTrainingName(trainingName));
        verify(jmsTemplate, never()).convertAndSend(any(Queue.class), anyString());
        verify(trainingRepository, never()).deleteAll(anyList());
    }

    @Test
    void testDeleteByTrainingNameTraineeAssigned() {
        // Arrange
        String trainingName = "AssignedTraining";
        Training training = new Training();
        Trainee trainee = new Trainee();
        trainee.setUser(new User(1L, "Trainee", "User", "trainee.user", "password", true, Role.ROLE_USER, Collections.emptyList()));
        training.setTrainee(trainee);
        when(trainingRepository.existsByTrainingName(trainingName)).thenReturn(true);
        when(trainingRepository.findByTrainingName(trainingName)).thenReturn(Collections.singletonList(training));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> trainingService.deleteByTrainingName(trainingName));
        verify(jmsTemplate, never()).convertAndSend(any(Queue.class), anyString());
        verify(trainingRepository, never()).deleteAll(anyList());
    }

    @Test
    void testDeleteByTrainingNameJsonProcessingException() throws JsonProcessingException {
        // Arrange
        String trainingName = "TestTraining";
        Training training = new Training();
        when(trainingRepository.existsByTrainingName(trainingName)).thenReturn(true);
        when(trainingRepository.findByTrainingName(trainingName)).thenReturn(Collections.singletonList(training));
        // Act & Assert
        assertThrows(RuntimeException.class, () -> trainingService.deleteByTrainingName(trainingName));
        verify(trainingRepository, never()).deleteAll(anyList());
    }

    @Test
    void testDelete() {
        // Arrange
        Training training = new Training();

        // Act
        trainingService.delete(training);

        // Assert
        verify(trainingRepository, times(1)).delete(training);
    }

    @Test
    void testDeleteTrainingNotFound() {
        // Arrange
        Training training = null;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> trainingService.delete(training));
        verify(trainingRepository, never()).delete(any());
    }

    @Test
    void testUpdateTrainingTrainee() {
        // Arrange
        Training training = new Training();

        // Act
        trainingService.updateTrainingTrainee(training);

        // Assert
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void testUpdateTrainingTraineeTrainingNotFound() {
        // Arrange
        Training training = null;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> trainingService.updateTrainingTrainee(training));
        verify(trainingRepository, never()).save(any());
    }
}
