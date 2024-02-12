package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.ChangeStatusRequest;
import com.epam.finalDemo.dto.request.TrainerRegistrationRequest;
import com.epam.finalDemo.dto.request.UpdateTraineeProfileRequest;
import com.epam.finalDemo.dto.request.UpdateTrainerProfileRequest;
import com.epam.finalDemo.service.TrainerService;
import com.epam.finalDemo.utils.ValidModule;
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
    public ResponseEntity<?> getProfile(@RequestParam String username) {
        try {
            validModule.getProfile(username);
            return ResponseEntity.ok(trainerService.getProfile(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateTrainerProfileRequest request) {
        try {
            validModule.updateProfileTrainer(request);
            return ResponseEntity.ok(trainerService.updateProfile(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatusRequest request) {
        try {
            validModule.changeStatus(request);
            return ResponseEntity.ok(trainerService.changeStatus(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
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
