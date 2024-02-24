package com.epam.finalDemo.controller;

import com.epam.finalDemo.client.TrainerClient;
import com.epam.finalDemo.domain.Training;
import com.epam.finalDemo.dto.request.ActionType;
import com.epam.finalDemo.dto.request.PostTrainingRequest;
import com.epam.finalDemo.dto.request.TrainerClientDTO;
import com.epam.finalDemo.service.TrainingService;
import com.epam.finalDemo.utils.ValidModule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final ValidModule validModule;
    private final TrainerClient client;

    @PostMapping("/create")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> createTraining(@RequestBody PostTrainingRequest request) {
        try {
            validModule.trainingRequest(request);
            Training training = trainingService.save(request);
            TrainerClientDTO trainerClient = TrainerClientDTO.builder()
                    .username(training.getTrainer().getUser().getUsername())
                    .firstName(training.getTrainer().getUser().getFirstName())
                    .lastName(training.getTrainer().getUser().getLastName())
                    .isActive(training.getTrainer().getUser().getIsActive())
                    .dateTime(training.getTrainingDate())
                    .duration(training.getDuration())
                    .actionType(ActionType.CREATE)
                    .build();
            client.save(trainerClient);
            return ResponseEntity.ok("Training created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteTraining(@RequestParam String trainingName) {
        try {
            trainingService.deleteByTrainingName(trainingName);
            return ResponseEntity.ok("Training deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
