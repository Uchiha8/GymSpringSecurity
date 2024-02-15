package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.*;
import com.epam.finalDemo.dto.request.ChangeStatusRequest;
import com.epam.finalDemo.dto.request.TraineeRegistrationRequest;
import com.epam.finalDemo.dto.request.UpdateTraineeProfileRequest;
import com.epam.finalDemo.dto.response.*;
import com.epam.finalDemo.repository.TraineeRepository;
import com.epam.finalDemo.repository.UserRepository;
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

public class TraineeServiceTest {
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        TraineeRegistrationRequest request = new TraineeRegistrationRequest("John", "Doe", null, "123 Main St");
        User user = new User(1L, "John", "Doe", "john.doe", "password", false, Role.ROLE_USER, Collections.emptyList());
        Trainee trainee = Trainee.builder()
                .dateOfBirth(request.dateOfBirth())
                .address(request.address())
                .user(user)
                .build();

        when(userService.register(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedJwtToken");
        when(userRepository.save(user)).thenReturn(user);
        when(traineeRepository.save(trainee)).thenReturn(trainee);

        // When
        RegistrationResponse response = traineeService.register(request);

        // Then
        assertNotNull(response);
        assertEquals(user.getUsername(), response.username());
        assertEquals(UserService.password, response.password());
        assertEquals("mockedJwtToken", response.token());

        // Verify interactions
        verify(userService, times(1)).register(any(User.class));
        verify(traineeRepository, times(1)).save(any(Trainee.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(userService, times(1)).savedUserTokens(eq("mockedJwtToken"), any(User.class));
    }

    @Test
    void testGetProfile() {
        // Given
        String username = "john.doe";
        Trainee trainee = new Trainee();
        trainee.setUser(new User(1L, "John", "Doe", "john.doe", "password", true, Role.ROLE_USER, Collections.emptyList()));
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main St");
        trainee.setTrainings(Collections.singletonList(createTraining()));

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));

        // When
        TraineeProfileResponse response = traineeService.getProfile(username);

        // Then
        assertNotNull(response);
        assertEquals("John", response.fistName());
        assertEquals("Doe", response.lastName());
        assertEquals(trainee.getDateOfBirth(), response.dateOfBirth());
        assertEquals(trainee.getAddress(), response.address());
        assertTrue(response.isActive());

        List<TrainerList> trainerLists = response.trainers();
        assertNotNull(trainerLists);
        assertEquals(1, trainerLists.size());

        TrainerList trainerList = trainerLists.get(0);
        assertEquals("john.doe", trainerList.username());
        assertEquals("John", trainerList.firstName());
        assertEquals("Doe", trainerList.lastName());
        assertEquals("Training Type", trainerList.trainingType());
    }

    private Training createTraining() {
        Trainer trainer = new Trainer();
        trainer.setUser(new User(2L, "John", "Doe", "john.doe", "password", true, Role.ROLE_USER, Collections.emptyList()));
        trainer.setTrainingType(new TrainingType(1L, "Training Type"));

        Training training = new Training();
        training.setTrainingName("Training 1");
        training.setTrainingType(trainer.getTrainingType());
        training.setTrainer(trainer);

        return training;
    }

    @Test
    void testGetTrainings() {
        // Given
        String username = "john.doe";
        Trainee trainee = new Trainee();
        trainee.setTrainings(Collections.singletonList(createTraining("Training 1")));

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));

        // When
        List<TraineeTrainingsResponse> trainingResponses = traineeService.getTrainings(username);

        // Then
        assertNotNull(trainingResponses);
        assertEquals(1, trainingResponses.size());

        TraineeTrainingsResponse response = trainingResponses.get(0);
        assertEquals("Training 1", response.trainingName());
        assertNotNull(response.trainingDate());
        assertEquals("Training Type", response.trainingType());
        assertNotNull(response.duration());
        assertEquals("trainer.one", response.trainerName());
    }

    private Training createTraining(String trainingName) {
        Trainer trainer = new Trainer();
        trainer.setUser(new User(2L, "Trainer", "One", "trainer.one", "password", true, Role.ROLE_USER, Collections.emptyList()));
        trainer.setTrainingType(new TrainingType(1L, "Training Type"));

        Training training = new Training();
        training.setTrainingName(trainingName);
        training.setTrainingType(trainer.getTrainingType());
        training.setTrainer(trainer);
        training.setTrainingDate(new Date());
        training.setDuration(Duration.ofHours(1));

        return training;
    }

    @Test
    void testUpdateProfile() {
        // Given
        String username = "john.doe";
        UpdateTraineeProfileRequest request = createUpdateTraineeProfileRequest();
        Trainee trainee = createTrainee(username);

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
        when(traineeRepository.save(trainee)).thenReturn(trainee);

        // When
        UpdateTraineeProfileResponse response = traineeService.updateProfile(request);

        // Then
        assertNotNull(response);
        assertEquals(username, response.username());
        assertEquals(request.firstName(), response.firstName());
        assertEquals(request.lastName(), response.lastName());
        assertEquals(request.dateOfBirth(), response.dateOfBirth());
        assertEquals(request.address(), response.address());
        assertEquals(request.isActive(), response.isActive());
        assertFalse(!response.trainers().isEmpty());
    }

    private UpdateTraineeProfileRequest createUpdateTraineeProfileRequest() {
        return new UpdateTraineeProfileRequest(
                "john.doe",
                "John",
                "Doe",
                null,
                "New Address",
                true
        );
    }

    private Trainee createTrainee(String username) {
        Trainee trainee = new Trainee();
        trainee.setUser(new User(1L, "John", "Doe", username, "password", true, Role.ROLE_USER, Collections.emptyList()));
        trainee.setDateOfBirth(null); // Set the actual date of birth
        trainee.setAddress("Old Address");
        trainee.setTrainings(Collections.emptyList()); // Add actual trainings if needed

        return trainee;
    }

    @Test
    void testDelete() {
        // Given
        String username = "john.doe";
        Trainee trainee = createTrainee(username);

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));

        // When
        traineeService.delete(username);

        // Then
        verify(traineeRepository, times(1)).delete(trainee);
    }

    @Test
    void testChangeStatus() {
        // Given
        String username = "john.doe";
        Trainee trainee = createTrainee(username);

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));

        ChangeStatusRequest changeStatusRequest = new ChangeStatusRequest(username, !trainee.getUser().getIsActive());

        // When
        boolean result = traineeService.changeStatus(changeStatusRequest);

        // Then
        assertTrue(result);
        verify(traineeRepository, times(1)).save(trainee);
        assertEquals(changeStatusRequest.status(), trainee.getUser().getIsActive());
    }
}
