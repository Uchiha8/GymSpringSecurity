package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.TrainingType;
import com.epam.finalDemo.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingType save(TrainingType trainingType) {
        return trainingTypeRepository.save(trainingType);
    }

    public TrainingType findByName(String name) {
        return trainingTypeRepository.findByName(name).orElse(null);
    }

    public List<TrainingType> getAll() {
        return trainingTypeRepository.findAll();
    }
}
