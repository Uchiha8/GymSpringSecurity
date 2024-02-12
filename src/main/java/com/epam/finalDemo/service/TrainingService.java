package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.Trainer;
import com.epam.finalDemo.domain.Training;
import com.epam.finalDemo.dto.request.PostTrainingRequest;
import com.epam.finalDemo.repository.TraineeRepository;
import com.epam.finalDemo.repository.TrainerRepository;
import com.epam.finalDemo.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    public Training save(PostTrainingRequest request) {
        if (trainingRepository.existsByTrainingName(request.trainingName())) {
            throw new RuntimeException("Training with name " + request.trainingName() + " already exists");
        } else if (!traineeRepository.existsByUserUsername(request.traineeUsername())) {
            throw new RuntimeException("Trainee with username " + request.traineeUsername() + " not found");
        } else if (!trainerRepository.existsByUserUsername(request.trainerUsername())) {
            throw new RuntimeException("Trainer with username " + request.trainerUsername() + " not found");
        } else {
            Trainer trainer  = trainerRepository.findByUserUsername(request.trainerUsername()).get();
            Training training = Training.builder()
                    .trainee(traineeRepository.findByUserUsername(request.traineeUsername()).get())
                    .trainer(trainer)
                    .trainingName(request.trainingName())
                    .trainingType(trainer.getTrainingType())
                    .trainingDate(request.trainingDate())
                    .duration(request.duration())
                    .build();
            return trainingRepository.save(training);
        }
    }
}
