package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.ChangeStatusRequest;
import com.epam.finalDemo.dto.request.TraineeRegistrationRequest;
import com.epam.finalDemo.dto.request.UpdateTraineeProfileRequest;
import com.epam.finalDemo.service.TraineeService;
import com.epam.finalDemo.utils.ValidModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trainee")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;
    private final ValidModule validModule;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TraineeRegistrationRequest request) {
        try {
            validModule.traineeRegister(request);
            return ResponseEntity.ok(traineeService.register(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam String username) {
        try {
            validModule.getProfile(username);
            return ResponseEntity.ok(traineeService.getProfile(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateTraineeProfileRequest request) {
        try {
            validModule.updateProfileTrainee(request);
            return ResponseEntity.ok(traineeService.updateProfile(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeStatusRequest request) {
        try {
            validModule.changeStatus(request);
            return ResponseEntity.ok(traineeService.changeStatus(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String username) {
        try {
            validModule.delete(username);
            traineeService.delete(username);
            return ResponseEntity.ok("Trainee deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
