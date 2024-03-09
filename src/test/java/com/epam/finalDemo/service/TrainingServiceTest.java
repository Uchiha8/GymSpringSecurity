package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.*;
import com.epam.finalDemo.dto.request.PostTrainingRequest;
import com.epam.finalDemo.repository.TraineeRepository;
import com.epam.finalDemo.repository.TrainerRepository;
import com.epam.finalDemo.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
}
