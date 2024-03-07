package com.epam.finalDemo.controller;

import com.epam.finalDemo.domain.Training;
import com.epam.finalDemo.dto.request.ActionType;
import com.epam.finalDemo.dto.request.PostTrainingRequest;
import com.epam.finalDemo.dto.request.TrainerClientDTO;
import com.epam.finalDemo.service.TrainingService;
import com.epam.finalDemo.utils.ValidModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final ValidModule validModule;
    private final JmsTemplate jmsTemplate;
    private final Queue queue;
    private final static Logger logger = LogManager.getLogger(TrainingController.class);

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
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(trainerClient);
            jmsTemplate.convertAndSend(queue, json);
            logger.info("Training created successfully");
            return ResponseEntity.ok("Training created successfully");
        } catch (Exception e) {
            logger.error("Error while creating training!!!");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteTraining(@RequestParam String trainingName) {
        try {
            trainingService.deleteByTrainingName(trainingName);
            logger.info("Training deleted successfully");
            return ResponseEntity.ok("Training deleted successfully");
        } catch (Exception e) {
            logger.error("Error while deleting training!!!");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
