package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.*;
import com.epam.finalDemo.service.TrainerService;
import com.epam.finalDemo.utils.ValidModule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private final ValidModule validModule;
    private final static Logger logger = LogManager.getLogger(TrainerController.class);

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TrainerRegistrationRequest request) {
        try {
            validModule.trainerRegister(request);
            logger.info("Trainer registered successfully");
            return ResponseEntity.ok(trainerService.register(request));
        } catch (Exception e) {
            logger.error("Error while registering trainer!!!");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getProfile(@RequestParam String username) {
        try {
            validModule.getProfile(username);
            logger.info("Profile returned for trainer with username " + username);
            return ResponseEntity.ok(trainerService.getProfile(username));
        } catch (Exception e) {
            logger.error("Error while getting profile for trainer with username " + username);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/trainings")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getTrainings(@RequestBody TrainerTrainingsRequest request) {
        try {
            validModule.getTrainingsTrainer(request);
            logger.info("Trainings returned for trainer with username " + request.username());
            return ResponseEntity.ok(trainerService.getTrainings(request));
        } catch (Exception e) {
            logger.error("Error while getting trainings for trainer with username " + request.username());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateProfile")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateTrainerProfileRequest request) {
        try {
            validModule.updateProfileTrainer(request);
            logger.info("Profile updated for trainer with username " + request.username());
            return ResponseEntity.ok(trainerService.updateProfile(request));
        } catch (Exception e) {
            logger.error("Error while updating profile for trainer with username " + request.username());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/changeStatus")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatusRequest request) {
        try {
            validModule.changeStatus(request);
            logger.info("Status changed for trainer with username " + request.username());
            return ResponseEntity.ok(trainerService.changeStatus(request));
        } catch (Exception e) {
            logger.error("Error while changing status for trainer with username " + request.username());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> delete(@RequestParam String username) {
        try {
            validModule.delete(username);
            trainerService.delete(username);
            logger.info("Trainer with username " + username + " deleted");
            return ResponseEntity.ok("Trainer deleted successfully");
        } catch (Exception e) {
            logger.error("Error while deleting trainer with username " + username);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/schedule")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getSchedule(@RequestParam String username) {
        try {
            validModule.getSchedule(username);
            trainerService.getSchedule(username);
            trainerService.test(username);
            logger.info("Schedule for trainer with username " + username + " returned");
            return ResponseEntity.ok(trainerService.test(username));
        } catch (Exception e) {
            logger.error("Error while getting schedule for trainer with username " + username);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
