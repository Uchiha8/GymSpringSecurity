package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.*;
import com.epam.finalDemo.service.TrainerService;
import com.epam.finalDemo.utils.ValidModule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private final ValidModule validModule;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TrainerRegistrationRequest request) {
        try {
            validModule.trainerRegister(request);
            return ResponseEntity.ok(trainerService.register(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getProfile(@RequestParam String username) {
        try {
            validModule.getProfile(username);
            return ResponseEntity.ok(trainerService.getProfile(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/trainings")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getTrainings(@RequestBody TrainerTrainingsRequest request) {
        try {
            validModule.getTrainingsTrainer(request);
            return ResponseEntity.ok(trainerService.getTrainings(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateProfile")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateTrainerProfileRequest request) {
        try {
            validModule.updateProfileTrainer(request);
            return ResponseEntity.ok(trainerService.updateProfile(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/changeStatus")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatusRequest request) {
        try {
            validModule.changeStatus(request);
            return ResponseEntity.ok(trainerService.changeStatus(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> delete(@RequestParam String username) {
        try {
            validModule.delete(username);
            trainerService.delete(username);
            return ResponseEntity.ok("Trainer deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
