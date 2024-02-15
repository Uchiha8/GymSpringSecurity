package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.*;
import com.epam.finalDemo.dto.request.ChangeStatusRequest;
import com.epam.finalDemo.dto.request.TrainerRegistrationRequest;
import com.epam.finalDemo.dto.request.TrainerTrainingsRequest;
import com.epam.finalDemo.dto.request.UpdateTrainerProfileRequest;
import com.epam.finalDemo.dto.response.*;
import com.epam.finalDemo.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final TrainingTypeService trainingTypeService;

    public RegistrationResponse register(TrainerRegistrationRequest request) {
        User user = new User(null, request.firstName(), request.lastName(), null, null, false, Role.ROLE_ADMIN, List.of());
        User entity = userService.register(user);
        TrainingType trainingType = trainingTypeService.findByName(request.trainingType());
        Trainer trainer = Trainer.builder()
                .trainingType(trainingType)
                .user(entity)
                .trainings(List.of())
                .build();
        trainerRepository.save(trainer);
        var jwtToken = jwtService.generateToken(entity);
        userService.savedUserTokens(jwtToken, entity);
        return RegistrationResponse.builder()
                .username(entity.getUsername())
                .password(UserService.password)
                .token(jwtToken)
                .build();
    }

    public TrainerProfileResponse getProfile(String username) {
        Trainer trainer = trainerRepository.findByUserUsername(username).orElseThrow(
                () -> new RuntimeException("Trainer with username " + username + " not found"));
        List<TraineeList> trainees = new ArrayList<>();
        if (!trainer.getTrainings().isEmpty()) {
            for (Training training : trainer.getTrainings()) {
                trainees.add(new TraineeList(
                        training.getTrainee().getUser().getUsername(),
                        training.getTrainee().getUser().getFirstName(),
                        training.getTrainee().getUser().getLastName()));
            }
        }
        return new TrainerProfileResponse(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getTrainingType().getName(),
                trainer.getUser().getIsActive(),
                trainees);
    }

    public List<TrainerTrainingsResponse> getTrainings(TrainerTrainingsRequest request) {
        var trainer = trainerRepository.findByUserUsername(request.username()).orElseThrow(
                () -> new RuntimeException("Trainer with username " + request.username() + " not found"));
        if (trainer.getTrainings().isEmpty()) {
            throw new RuntimeException("Trainer with username " + request.username() + " has no trainings");
        }
        List<TrainerTrainingsResponse> trainings = new ArrayList<>();
        for (Training training : trainer.getTrainings()) {
            trainings.add(new TrainerTrainingsResponse(
                    training.getTrainingName(),
                    training.getTrainingDate(),
                    training.getTrainingType().getName(),
                    training.getDuration(),
                    training.getTrainee().getUser().getUsername()
            ));
        }
        return trainings;
    }

    public UpdateTrainerProfileResponse updateProfile(UpdateTrainerProfileRequest request) {
        var trainer = trainerRepository.findByUserUsername(request.username()).orElseThrow(
                () -> new RuntimeException("Trainer with username " + request.username() + " not found"));
        List<TraineeList> trainees = new ArrayList<>();
        if (!trainer.getTrainings().isEmpty()) {
            for (Training training : trainer.getTrainings()) {
                trainees.add(new TraineeList(
                        training.getTrainee().getUser().getUsername(),
                        training.getTrainee().getUser().getFirstName(),
                        training.getTrainee().getUser().getLastName()));
            }
        }
        trainer.getUser().setFirstName(request.firstName());
        trainer.getUser().setLastName(request.lastName());
        TrainingType trainingType = trainingTypeService.findByName(request.trainingType());
        if (trainingType == null) {
            throw new RuntimeException("Training type " + request.trainingType() + " not found");
        }
        trainer.setTrainingType(trainingType);
        trainer.getUser().setIsActive(request.isActive());
        return new UpdateTrainerProfileResponse(
                trainer.getUser().getUsername(),
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainer.getTrainingType().getName(),
                trainer.getUser().getIsActive(),
                trainees);
    }

    public void delete(String username) {
        var trainer = trainerRepository.findByUserUsername(username).orElseThrow(
                () -> new RuntimeException("Trainer with username " + username + " not found"));
        trainerRepository.delete(trainer);
    }

    public boolean changeStatus(ChangeStatusRequest request) {
        var trainer = trainerRepository.findByUserUsername(request.username()).orElseThrow(
                () -> new RuntimeException("Trainer with username " + request.username() + " not found"));
        if (trainer.getUser().getIsActive() == request.status()) {
            throw new RuntimeException("Trainer with username " + request.username() + " already has status " + request.status());
        }
        trainer.getUser().setIsActive(request.status());
        trainerRepository.save(trainer);
        return true;
    }
}
