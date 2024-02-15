package com.epam.finalDemo.service;

import com.epam.finalDemo.domain.TrainingType;
import com.epam.finalDemo.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TrainingTypeServiceTest {
    @InjectMocks
    private TrainingTypeService trainingTypeService;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Given
        TrainingType trainingType = new TrainingType(1L, "Java");

        // When
        when(trainingTypeRepository.save(trainingType)).thenReturn(trainingType);
        TrainingType result = trainingTypeService.save(trainingType);

        // Then
        verify(trainingTypeRepository, times(1)).save(trainingType);
        assertEquals(trainingType, result);
    }

    @Test
    void testFindByName() {
        // Given
        TrainingType expectedTrainingType = new TrainingType(1L, "Java");

        // When
        when(trainingTypeRepository.findByName(expectedTrainingType.getName())).thenReturn(Optional.of(expectedTrainingType));
        TrainingType result = trainingTypeService.findByName(expectedTrainingType.getName());

        // Then
        verify(trainingTypeRepository, times(1)).findByName(expectedTrainingType.getName());
        assertEquals(expectedTrainingType, result);
    }

    @Test
    void testFindByNameNotFound() {
        // Given
        String trainingTypeName = "NonExistentType";

        // When
        when(trainingTypeRepository.findByName(trainingTypeName)).thenReturn(Optional.empty());
        TrainingType result = trainingTypeService.findByName(trainingTypeName);

        // Then
        verify(trainingTypeRepository, times(1)).findByName(trainingTypeName);
        assertNull(result);
    }

    @Test
    void testGetAll() {
        // Given
        List<TrainingType> expectedTrainingTypes = List.of(
                new TrainingType(1L,"Java"),
                new TrainingType(2L,"Python"),
                new TrainingType(3L,"JavaScript")
        );

        // When
        when(trainingTypeRepository.findAll()).thenReturn(expectedTrainingTypes);
        List<TrainingType> result = trainingTypeService.getAll();

        // Then
        verify(trainingTypeRepository, times(1)).findAll();
        assertEquals(expectedTrainingTypes, result);
    }
}
