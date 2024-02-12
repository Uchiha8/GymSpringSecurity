package com.epam.finalDemo.controller;

import com.epam.finalDemo.dto.request.PostTrainingRequest;
import com.epam.finalDemo.service.TrainingService;
import com.epam.finalDemo.utils.ValidModule;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final ValidModule validModule;

    @PostMapping("/create")
    public ResponseEntity<?> createTraining(@RequestBody PostTrainingRequest request) {
        try {
            validModule.trainingRequest(request);
            trainingService.save(request);
            return ResponseEntity.ok("Training created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
