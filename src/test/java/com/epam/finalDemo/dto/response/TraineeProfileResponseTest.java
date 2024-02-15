package com.epam.finalDemo.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraineeProfileResponseTest {

    @Test
    public void testRecordEquality() {
        List<TrainerList> trainers1 = new ArrayList<>();
        trainers1.add(new TrainerList("trainer1", "John", "Doe", "Java"));
        trainers1.add(new TrainerList("trainer2", "Jane", "Smith", "Python"));

        List<TrainerList> trainers2 = new ArrayList<>();
        trainers2.add(new TrainerList("trainer1", "John", "Doe", "Java"));
        trainers2.add(new TrainerList("trainer2", "Jane", "Smith", "Python"));

        List<TrainerList> trainers3 = new ArrayList<>();
        trainers3.add(new TrainerList("trainer3", "Bob", "Johnson", "C++"));

        Date date = new Date();
        TraineeProfileResponse response1 = new TraineeProfileResponse("John", "Doe", date, "123 Main St", true, trainers1);
        TraineeProfileResponse response2 = new TraineeProfileResponse("John", "Doe", date, "123 Main St", true, trainers2);
        TraineeProfileResponse response3 = new TraineeProfileResponse("Alice", "Smith", date, "456 Oak St", false, trainers3);

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToStringMethod() {
        List<TrainerList> trainers = new ArrayList<>();
        trainers.add(new TrainerList("trainer1", "John", "Doe", "Java"));
        trainers.add(new TrainerList("trainer2", "Jane", "Smith", "Python"));

        TraineeProfileResponse response = new TraineeProfileResponse("John", "Doe", new Date(), "123 Main St", true, trainers);

        assertEquals("TraineeProfileResponse[fistName=John, lastName=Doe, dateOfBirth=" + response.dateOfBirth() +
                ", address=123 Main St, isActive=true, trainers=" + trainers + "]", response.toString());
    }
}

