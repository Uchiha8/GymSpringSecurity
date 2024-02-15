package com.epam.finalDemo.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class TraineeTest {

    @Test
    public void testTraineeBuilder() {
        // Given
        Long id = 1L;
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        User user = new User(); // You may need to create a User object with appropriate data.
        List<Training> trainings = new ArrayList<>(); // You may need to create Training objects with appropriate data.

        // When
        Trainee trainee = Trainee.builder()
                .id(id)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .user(user)
                .trainings(trainings)
                .build();

        // Then
        assertNotNull(trainee);
        assertEquals(id, trainee.getId());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertEquals(address, trainee.getAddress());
        assertEquals(user, trainee.getUser());
        assertEquals(trainings, trainee.getTrainings());
    }

    @Test
    public void testNoArgsConstructor() {
        Trainee trainee = new Trainee();
        assertNotNull(trainee);
    }

    @Test
    public void testAllArgsConstructor() {
        User user = Mockito.mock(User.class);
        List<Training> trainings = new ArrayList<>();
        trainings.add(Mockito.mock(Training.class));

        Trainee trainee = new Trainee(1L, new Date(), "Address", user, trainings);

        assertNotNull(trainee);
        assertEquals(1L, trainee.getId());
        assertNotNull(trainee.getDateOfBirth());
        assertEquals("Address", trainee.getAddress());
        assertEquals(user, trainee.getUser());
        assertEquals(trainings, trainee.getTrainings());
    }

    @Test
    public void testSetterAndGetterMethods() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("Address");

        assertEquals(1L, trainee.getId());
        assertNotNull(trainee.getDateOfBirth());
        assertEquals("Address", trainee.getAddress());
    }

    @Test
    public void testUserAssociation() {
        Trainee trainee = new Trainee();
        User user = Mockito.mock(User.class);
        trainee.setUser(user);

        assertEquals(user, trainee.getUser());
    }

    @Test
    public void testTrainingAssociation() {
        Trainee trainee = new Trainee();
        List<Training> trainings = new ArrayList<>();
        trainings.add(Mockito.mock(Training.class));
        trainee.setTrainings(trainings);

        assertEquals(trainings, trainee.getTrainings());
    }
}
