package com.epam.finalDemo.service;

import com.epam.finalDemo.client.TrainerClient;
import com.epam.finalDemo.domain.Trainer;
import com.epam.finalDemo.domain.Training;
import com.epam.finalDemo.dto.request.ActionType;
import com.epam.finalDemo.dto.request.PostTrainingRequest;
import com.epam.finalDemo.dto.request.TrainerClientDTO;
import com.epam.finalDemo.repository.TraineeRepository;
import com.epam.finalDemo.repository.TrainerRepository;
import com.epam.finalDemo.repository.TrainingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.jms.Queue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final JmsTemplate jmsTemplate;
    private final Queue queue;

    public Training save(PostTrainingRequest request) {
        if (!traineeRepository.existsByUserUsername(request.traineeUsername())) {
            throw new RuntimeException("Trainee with username " + request.traineeUsername() + " not found");
        } else if (!trainerRepository.existsByUserUsername(request.trainerUsername())) {
            throw new RuntimeException("Trainer with username " + request.trainerUsername() + " not found");
        } else {
            Trainer trainer = trainerRepository.findByUserUsername(request.trainerUsername()).get();
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

    public void deleteByTrainingName(String trainingName) {
        if (!trainingRepository.existsByTrainingName(trainingName)) {
            throw new RuntimeException("Training with name " + trainingName + " not found");
        }
        List<Training> training = trainingRepository.findByTrainingName(trainingName);
        List<TrainerClientDTO> dtoList = new ArrayList<>();
        for (Training t : training) {
            if (t.getTrainee() == null) {
                dtoList.add(TrainerClientDTO.builder()
                        .username(t.getTrainer().getUser().getUsername())
                        .firstName(t.getTrainer().getUser().getFirstName())
                        .lastName(t.getTrainer().getUser().getLastName())
                        .isActive(t.getTrainer().getUser().getIsActive())
                        .dateTime(t.getTrainingDate())
                        .duration(t.getDuration())
                        .actionType(ActionType.DELETE)
                        .build());
            } else {
                throw new RuntimeException("Trainee with username " + t.getTrainee().getUser().getUsername() + " is assigned to training");
            }
        }
//        client.saveAll(dtoList);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(dtoList);
            jmsTemplate.convertAndSend(queue, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        trainingRepository.deleteAll(training);
    }

    public void delete(Training training) {
        if (training == null) {
            throw new RuntimeException("Training not found");
        }
        trainingRepository.delete(training);
    }

    public void updateTrainingTrainee(Training training) {
        if (training == null) {
            throw new RuntimeException("Training not found");
        }
        training.setTrainee(null);
        trainingRepository.save(training);
    }
}
