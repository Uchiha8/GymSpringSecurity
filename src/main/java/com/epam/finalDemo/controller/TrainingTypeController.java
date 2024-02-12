package com.epam.finalDemo.controller;

import com.epam.finalDemo.domain.TrainingType;
import com.epam.finalDemo.service.TrainingTypeService;
import com.epam.finalDemo.utils.ValidModule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainingType")
@RequiredArgsConstructor
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;
    private final ValidModule validModule;

    @PostMapping("/register")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> save(@RequestBody TrainingType request) {
        try {
            validModule.trainingTypeRegister(request);
            return ResponseEntity.ok(trainingTypeService.save(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAll() {
        List<TrainingType> trainingTypes = trainingTypeService.getAll();
        if (trainingTypes.isEmpty()) {
            return ResponseEntity.badRequest().body("No training types found");
        }
        return ResponseEntity.ok(trainingTypeService.getAll());
    }
}
