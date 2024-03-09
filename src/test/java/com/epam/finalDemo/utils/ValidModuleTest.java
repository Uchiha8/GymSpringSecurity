package com.epam.finalDemo.utils;

import com.epam.finalDemo.domain.TrainingType;
import com.epam.finalDemo.dto.request.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ValidModuleTest {
    @Test
    public void testTraineeRegisterWithValidRequest() {
        // Given
        TraineeRegistrationRequest validRequest = new TraineeRegistrationRequest("John", "Doe", new Date(), "123 Main St");

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.traineeRegister(validRequest));
    }

    @Test
    public void testTraineeRegisterWithNullFirstName() {
        // Given
        TraineeRegistrationRequest invalidRequest = new TraineeRegistrationRequest(null, "Doe", new Date(), "123 Main St");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.traineeRegister(invalidRequest));

        // Then
        assertEquals("First name is required", exception.getMessage());
    }

    @Test
    public void testTraineeRegisterWithNullLastName() {
        // Given
        TraineeRegistrationRequest invalidRequest = new TraineeRegistrationRequest("John", null, new Date(), "123 Main St");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.traineeRegister(invalidRequest));

        // Then
        assertEquals("Last name is required", exception.getMessage());
    }


    @Test
    public void testTrainerRegisterWithValidRequest() {
        // Given
        TrainerRegistrationRequest validRequest = new TrainerRegistrationRequest("John", "Doe", "Java");

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.trainerRegister(validRequest));
    }

    @Test
    public void testTrainerRegisterWithNullFirstName() {
        // Given
        TrainerRegistrationRequest invalidRequest = new TrainerRegistrationRequest(null, "Doe", "Java");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainerRegister(invalidRequest));

        // Then
        assertEquals("First name is required", exception.getMessage());
    }

    @Test
    public void testTrainerRegisterWithNullLastName() {
        // Given
        TrainerRegistrationRequest invalidRequest = new TrainerRegistrationRequest("John", null, "Java");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainerRegister(invalidRequest));

        // Then
        assertEquals("Last name is required", exception.getMessage());
    }

    @Test
    public void testTrainerRegisterWithNullTrainingType() {
        // Given
        TrainerRegistrationRequest invalidRequest = new TrainerRegistrationRequest("John", "Doe", null);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainerRegister(invalidRequest));

        // Then
        assertEquals("Training type is required", exception.getMessage());
    }

    @Test
    public void testTrainingTypeRegisterWithValidRequest() {
        // Given
        TrainingType validRequest = new TrainingType(1L, "Java");

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.trainingTypeRegister(validRequest));
    }

    @Test
    public void testTrainingTypeRegisterWithNullName() {
        // Given
        TrainingType invalidRequest = new TrainingType();

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainingTypeRegister(invalidRequest));

        // Then
        assertEquals("Training type name is required", exception.getMessage());
    }

    @Test
    public void testChangePasswordWithValidRequest() {
        // Given
        ChangeLoginRequest validRequest = new ChangeLoginRequest("john.doe", "oldPassword", "newPassword");

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.changePassword(validRequest));
    }

    @Test
    public void testChangePasswordWithNullUsername() {
        // Given
        ChangeLoginRequest invalidRequest = new ChangeLoginRequest(null, "oldPassword", "newPassword");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.changePassword(invalidRequest));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testChangePasswordWithNullOldPassword() {
        // Given
        ChangeLoginRequest invalidRequest = new ChangeLoginRequest("john.doe", null, "newPassword");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.changePassword(invalidRequest));

        // Then
        assertEquals("Old password is required", exception.getMessage());
    }

    @Test
    public void testChangePasswordWithNullNewPassword() {
        // Given
        ChangeLoginRequest invalidRequest = new ChangeLoginRequest("john.doe", "oldPassword", null);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.changePassword(invalidRequest));

        // Then
        assertEquals("New password is required", exception.getMessage());
    }

    @Test
    public void testTrainingRequestWithValidRequest() {
        // Given
        PostTrainingRequest validRequest = new PostTrainingRequest("trainee1", "trainer1", "Java Training", new Date(), Duration.ofHours(2));

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.trainingRequest(validRequest));
    }

    @Test
    public void testTrainingRequestWithNullTrainingName() {
        // Given
        PostTrainingRequest invalidRequest = new PostTrainingRequest("trainee1", "trainer1", null, new Date(), Duration.ofHours(2));

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainingRequest(invalidRequest));

        // Then
        assertEquals("Training name is required", exception.getMessage());
    }

    @Test
    public void testTrainingRequestWithNullTraineeUsername() {
        // Given
        PostTrainingRequest invalidRequest = new PostTrainingRequest(null, "trainer1", "Java Training", new Date(), Duration.ofHours(2));

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainingRequest(invalidRequest));

        // Then
        assertEquals("Trainee username is required", exception.getMessage());
    }

    @Test
    public void testTrainingRequestWithNullTrainerUsername() {
        // Given
        PostTrainingRequest invalidRequest = new PostTrainingRequest("trainee1", null, "Java Training", new Date(), Duration.ofHours(2));

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainingRequest(invalidRequest));

        // Then
        assertEquals("Trainer username is required", exception.getMessage());
    }

    @Test
    public void testTrainingRequestWithNullTrainingDate() {
        // Given
        PostTrainingRequest invalidRequest = new PostTrainingRequest("trainee1", "trainer1", "Java Training", null, Duration.ofHours(2));

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainingRequest(invalidRequest));

        // Then
        assertEquals("Training date is required", exception.getMessage());
    }

    @Test
    public void testTrainingRequestWithNullDuration() {
        // Given
        PostTrainingRequest invalidRequest = new PostTrainingRequest("trainee1", "trainer1", "Java Training", new Date(), null);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.trainingRequest(invalidRequest));

        // Then
        assertEquals("Duration is required", exception.getMessage());
    }

    @Test
    public void testChangeStatusWithValidRequest() {
        // Given
        ChangeStatusRequest validRequest = new ChangeStatusRequest("john.doe", true);

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.changeStatus(validRequest));
    }

    @Test
    public void testChangeStatusWithNullUsername() {
        // Given
        ChangeStatusRequest invalidRequest = new ChangeStatusRequest(null, true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.changeStatus(invalidRequest));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testChangeStatusWithNullStatus() {
        // Given
        ChangeStatusRequest invalidRequest = new ChangeStatusRequest("john.doe", null);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.changeStatus(invalidRequest));

        // Then
        assertEquals("Status is required", exception.getMessage());
    }

    @Test
    public void testGetProfileWithValidUsername() {
        // Given
        String validUsername = "john.doe";

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.getProfile(validUsername));
    }

    @Test
    public void testGetProfileWithNullUsername() {
        // Given
        String invalidUsername = null;

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.getProfile(invalidUsername));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testDeleteWithValidUsername() {
        // Given
        String validUsername = "john.doe";

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.delete(validUsername));
    }

    @Test
    public void testDeleteWithNullUsername() {
        // Given
        String invalidUsername = null;

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.delete(invalidUsername));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTraineeWithValidRequest() {
        // Given
        UpdateTraineeProfileRequest validRequest = new UpdateTraineeProfileRequest("john.doe", "John", "Doe", new Date(), "Main street", true);

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.updateProfileTrainee(validRequest));
    }

    @Test
    public void testUpdateProfileTraineeWithNullUsername() {
        // Given
        UpdateTraineeProfileRequest invalidRequest = new UpdateTraineeProfileRequest(null, "John", "Doe", null, null, true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainee(invalidRequest));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTraineeWithNullFirstName() {
        // Given
        UpdateTraineeProfileRequest invalidRequest = new UpdateTraineeProfileRequest("john.doe", null, "Doe", null, null, true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainee(invalidRequest));

        // Then
        assertEquals("First name is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTraineeWithNullLastName() {
        // Given
        UpdateTraineeProfileRequest invalidRequest = new UpdateTraineeProfileRequest("john.doe", "John", null, null, null, true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainee(invalidRequest));

        // Then
        assertEquals("Last name is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTraineeWithNullIsActive() {
        // Given
        UpdateTraineeProfileRequest invalidRequest = new UpdateTraineeProfileRequest("john.doe", "John", "Doe", null, "address", null);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainee(invalidRequest));

        // Then
        assertEquals("Status is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTrainerWithValidRequest() {
        // Given
        UpdateTrainerProfileRequest validRequest = new UpdateTrainerProfileRequest("john.doe", "John", "Doe", "Java", true);

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.updateProfileTrainer(validRequest));
    }

    @Test
    public void testUpdateProfileTrainerWithNullUsername() {
        // Given
        UpdateTrainerProfileRequest invalidRequest = new UpdateTrainerProfileRequest(null, "John", "Doe", "Java", true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainer(invalidRequest));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTrainerWithNullFirstName() {
        // Given
        UpdateTrainerProfileRequest invalidRequest = new UpdateTrainerProfileRequest("john.doe", null, "Doe", "Java", true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainer(invalidRequest));

        // Then
        assertEquals("First name is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTrainerWithNullLastName() {
        // Given
        UpdateTrainerProfileRequest invalidRequest = new UpdateTrainerProfileRequest("john.doe", "John", null, "Java", true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainer(invalidRequest));

        // Then
        assertEquals("Last name is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTrainerWithNullTrainingType() {
        // Given
        UpdateTrainerProfileRequest invalidRequest = new UpdateTrainerProfileRequest("john.doe", "John", "Doe", null, true);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainer(invalidRequest));

        // Then
        assertEquals("Training type is required", exception.getMessage());
    }

    @Test
    public void testUpdateProfileTrainerWithNullIsActive() {
        // Given
        UpdateTrainerProfileRequest invalidRequest = new UpdateTrainerProfileRequest("john.doe", "John", "Doe", "Java", null);

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.updateProfileTrainer(invalidRequest));

        // Then
        assertEquals("Status is required", exception.getMessage());
    }

    @Test
    public void testGetTrainingsTraineeWithValidRequest() {
        // Given
        TraineeTrainingsRequest validRequest = new TraineeTrainingsRequest("trainee1", new Date(), new Date(), "trainer1", "Java");

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.getTrainingsTrainee(validRequest));
    }

    @Test
    public void testGetTrainingsTraineeWithNullUsername() {
        // Given
        TraineeTrainingsRequest invalidRequest = new TraineeTrainingsRequest(null, new Date(), new Date(), "trainer1", "Java");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.getTrainingsTrainee(invalidRequest));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testGetTrainingsTrainerWithValidRequest() {
        // Given
        TrainerTrainingsRequest validRequest = new TrainerTrainingsRequest("trainer1", new Date(), new Date(), "trainee1");

        // When
        ValidModule validModule = new ValidModule();
        assertDoesNotThrow(() -> validModule.getTrainingsTrainer(validRequest));
    }

    @Test
    public void testGetTrainingsTrainerWithNullUsername() {
        // Given
        TrainerTrainingsRequest invalidRequest = new TrainerTrainingsRequest(null, new Date(), new Date(), "trainee1");

        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.getTrainingsTrainer(invalidRequest));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }


    @Test
    void testGetSchedule_NullUsername() {
        String username = null;
        // When
        ValidModule validModule = new ValidModule();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validModule.getSchedule(username));

        // Then
        assertEquals("Username is required", exception.getMessage());
    }
}
