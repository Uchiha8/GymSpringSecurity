package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.*;
import com.epam.finalDemo.dto.request.ChangeStatusRequest;
import com.epam.finalDemo.dto.request.TraineeRegistrationRequest;
import com.epam.finalDemo.dto.request.UpdateTraineeProfileRequest;
import com.epam.finalDemo.dto.response.*;
import com.epam.finalDemo.repository.TokenRepository;
import com.epam.finalDemo.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public RegistrationResponse register(TraineeRegistrationRequest request) {
        User user = new User(null, request.firstName(), request.lastName(), null, null, false, Role.ROLE_USER, List.of());
        var entity = userService.register(user);
        Trainee trainee = Trainee.builder()
                .dateOfBirth(request.dateOfBirth())
                .address(request.address())
                .user(entity)
                .build();
        traineeRepository.save(trainee);
        var jwtToken = jwtService.generateToken(entity);
        userService.savedUserTokens(jwtToken, entity);
        return RegistrationResponse.builder()
                .username(entity.getUsername())
                .password(UserService.password)
                .token(jwtToken)
                .build();
    }

    public TraineeProfileResponse getProfile(String username) {
        var trainee = traineeRepository.findByUserUsername(username).orElseThrow(
                () -> new RuntimeException("Trainee with username " + username + " not found"));
        List<Training> trainings = trainee.getTrainings();
        List<TrainerList> trainerLists = new ArrayList<>();
        if (!trainings.isEmpty()) {
            List<Trainer> trainers = new ArrayList<>();
            for (Training training : trainings) {
                trainers.add(training.getTrainer());
            }
            for (Trainer trainer : trainers) {
                trainerLists.add(new TrainerList(
                        trainer.getUser().getUsername(),
                        trainer.getUser().getFirstName(),
                        trainer.getUser().getLastName(),
                        trainer.getTrainingType().getName()));
            }
        }
        return new TraineeProfileResponse(
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                trainee.getUser().getIsActive(),
                trainerLists
        );
    }

    public UpdateTraineeProfileResponse updateProfile(UpdateTraineeProfileRequest request) {
        var trainee = traineeRepository.findByUserUsername(request.username()).orElseThrow(
                () -> new RuntimeException("Trainee with username " + request.username() + " not found"));
        List<TrainerList> trainers = new ArrayList<>();
        if (!trainee.getTrainings().isEmpty()) {
            for (Training training : trainee.getTrainings()) {
                trainers.add(new TrainerList(
                        training.getTrainer().getUser().getUsername(),
                        training.getTrainer().getUser().getFirstName(),
                        training.getTrainer().getUser().getLastName(),
                        training.getTrainer().getTrainingType().getName()));
            }
        }
        trainee.getUser().setFirstName(request.firstName());
        trainee.getUser().setLastName(request.lastName());
        trainee.setDateOfBirth(request.dateOfBirth());
        trainee.setAddress(request.address());
        trainee.getUser().setIsActive(request.isActive());
        traineeRepository.save(trainee);
        return new UpdateTraineeProfileResponse(
                trainee.getUser().getUsername(),
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                trainee.getUser().getIsActive(),
                trainers
        );
    }

    public void delete(String username) {
        var trainee = traineeRepository.findByUserUsername(username).orElseThrow(
                () -> new RuntimeException("Trainee with username " + username + " not found"));
        traineeRepository.delete(trainee);
    }

    public boolean changeStatus(ChangeStatusRequest request) {
        var trainee = traineeRepository.findByUserUsername(request.username()).orElseThrow(
                () -> new RuntimeException("Trainee with username " + request.username() + " not found"));
        if (trainee.getUser().getIsActive() == request.status()) {
            throw new RuntimeException("Trainee with username " + request.username() + " already has status " + request.status());
        }
        trainee.getUser().setIsActive(request.status());
        traineeRepository.save(trainee);
        return true;
    }
}
