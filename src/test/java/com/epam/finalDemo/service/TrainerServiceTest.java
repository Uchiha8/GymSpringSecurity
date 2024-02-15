package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.*;
import com.epam.finalDemo.dto.request.ChangeStatusRequest;
import com.epam.finalDemo.dto.request.TrainerTrainingsRequest;
import com.epam.finalDemo.dto.request.UpdateTrainerProfileRequest;
import com.epam.finalDemo.dto.response.TrainerProfileResponse;
import com.epam.finalDemo.dto.response.TrainerTrainingsResponse;
import com.epam.finalDemo.dto.response.UpdateTrainerProfileResponse;
import com.epam.finalDemo.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingTypeService trainingTypeService;
    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProfile() {
        // Given
        String username = "john.doe";
        Trainer trainer = createTrainerWithTrainings(username);

        when(trainerRepository.findByUserUsername(username)).thenReturn(java.util.Optional.of(trainer));

        // When
        TrainerProfileResponse result = trainerService.getProfile(username);

        // Then
        assertNotNull(result);
        verify(trainerRepository, times(1)).findByUserUsername(username);
        assertEquals(trainer.getUser().getFirstName(), result.fistName());
        assertEquals(trainer.getUser().getLastName(), result.lastName());
        assertEquals(trainer.getTrainingType().getName(), result.trainingType());
        assertEquals(trainer.getUser().getIsActive(), result.isActive());
    }

    @Test
    void testGetTrainings() {
        // Given
        String username = "john.doe";
        Trainer trainer = createTrainerWithTrainings(username);

        when(trainerRepository.findByUserUsername(username)).thenReturn(java.util.Optional.of(trainer));

        TrainerTrainingsRequest trainingsRequest = createTrainerTrainingsRequest(username);

        // When
        List<TrainerTrainingsResponse> result = trainerService.getTrainings(trainingsRequest);

        // Then
        assertNotNull(result);
        verify(trainerRepository, times(1)).findByUserUsername(username);
        assertEquals(2, result.size());
    }

    private Trainer createTrainerWithTrainings(String username) {
        Trainer trainer = new Trainer();
        trainer.setUser(new User(1L, "John", "Doe", username, "password", true, Role.ROLE_ADMIN, Collections.emptyList()));
        trainer.setTrainingType(new TrainingType(1L, "TrainingType")); // Add the actual training type

        // Add example trainings
        Training training1 = createTraining("Training1", new Date(), Duration.ofHours(2), "trainee1");
        Training training2 = createTraining("Training2", new Date(), Duration.ofHours(3), "trainee2");

        trainer.setTrainings(List.of(training1, training2));

        return trainer;
    }

    private Training createTraining(String trainingName, Date trainingDate, Duration duration, String traineeUsername) {
        TrainingType trainingType = new TrainingType(1L, "TrainingType");
        Trainee trainee = new Trainee();
        trainee.setUser(new User(2L, "Trainee", "User", traineeUsername, "password", true, Role.ROLE_USER, Collections.emptyList()));

        Training training = new Training();
        training.setTrainingName(trainingName);
        training.setTrainingDate(trainingDate);
        training.setDuration(duration);
        training.setTrainingType(trainingType);
        training.setTrainer(createTrainer(trainee.getUser().getUsername()));
        training.setTrainee(trainee);

        return training;
    }

    private Trainer createTrainer(String username) {
        Trainer trainer = new Trainer();
        trainer.setUser(new User(1L, "John", "Doe", username, "password", true, Role.ROLE_ADMIN, Collections.emptyList()));
        trainer.setTrainingType(new TrainingType(1L, "TrainingType"));

        return trainer;
    }

    private TrainerTrainingsRequest createTrainerTrainingsRequest(String username) {
        return new TrainerTrainingsRequest(username, new Date(), new Date(), "trainee1");
    }

    @Test
    void testUpdateProfile() {
        // Given
        String username = "john.doe";
        Trainer trainer = createTrainerWithTrainings();

        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));
        when(trainingTypeService.findByName(anyString())).thenReturn(new TrainingType(1L, "TrainingType"));

        UpdateTrainerProfileRequest updateRequest = createUpdateTrainerProfileRequest(username);

        // When
        UpdateTrainerProfileResponse result = trainerService.updateProfile(updateRequest);

        // Then
        assertNotNull(result);
        verify(trainerRepository, times(1)).findByUserUsername(username);
        verify(trainingTypeService, times(1)).findByName(updateRequest.trainingType());

        assertEquals(username, result.username());
    }

    private Trainer createTrainerWithTrainings() {
        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee(3L, new Date(), "address", new User(1L, "John", "Doe", "john.doe", "password", true, Role.ROLE_ADMIN, Collections.emptyList()), Collections.emptyList());
        trainer.setUser(new User(1L, "John", "Doe", "john.doe", "password", true, Role.ROLE_ADMIN, Collections.emptyList()));
        trainer.setTrainingType(new TrainingType(1L, "TrainingType"));
        Training training = new Training(1L, trainee, trainer, "Training Name", trainer.getTrainingType(), new Date(), Duration.ofHours(2));
        trainer.setTrainings(List.of(training));
        return trainer;
    }

    private UpdateTrainerProfileRequest createUpdateTrainerProfileRequest(String username) {
        return new UpdateTrainerProfileRequest(username, "NewFirstName", "NewLastName", "NewTrainingType", true);
    }

    @Test
    void testDelete() {
        // Given
        String username = "john.doe";
        Trainer trainer = createTrainer(username);

        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));
        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));

        // When
        trainerService.delete(username);

        // Then
        verify(trainerRepository, times(1)).delete(trainer);
    }

    @Test
    void testChangeStatus() {
        // Given
        String username = "john.doe";
        Trainer trainer = createTrainer(username);

        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));
        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));

        ChangeStatusRequest changeStatusRequest = new ChangeStatusRequest(username, !trainer.getUser().getIsActive());

        // When
        boolean result = trainerService.changeStatus(changeStatusRequest);

        // Then
        assertTrue(result);
        verify(trainerRepository, times(1)).save(trainer);
        assertEquals(changeStatusRequest.status(), trainer.getUser().getIsActive());
    }

}
