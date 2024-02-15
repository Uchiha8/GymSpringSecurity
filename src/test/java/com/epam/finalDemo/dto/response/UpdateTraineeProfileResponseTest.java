package com.epam.finalDemo.dto.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateTraineeProfileResponseTest {

    @Test
    public void testRecordEquality() {
        List<TrainerList> trainers1 = new ArrayList<>();
        trainers1.add(new TrainerList("trainer1", "John", "Doe", "Java"));
        trainers1.add(new TrainerList("trainer2", "Jane", "Smith", "Python"));

        List<TrainerList> trainers2 = new ArrayList<>();
        trainers2.add(new TrainerList("trainer1", "John", "Doe", "Java"));
        trainers2.add(new TrainerList("trainer2", "Jane", "Smith", "Python"));

        List<TrainerList> trainers3 = new ArrayList<>();
        trainers3.add(new TrainerList("trainer3", "Bob", "Johnson", "C#"));

        UpdateTraineeProfileResponse response1 = new UpdateTraineeProfileResponse("john.doe", "John", "Doe", new Date(), "123 Main St", true, trainers1);
        UpdateTraineeProfileResponse response2 = new UpdateTraineeProfileResponse("john.doe", "John", "Doe", new Date(), "123 Main St", true, trainers2);
        UpdateTraineeProfileResponse response3 = new UpdateTraineeProfileResponse("jane.smith", "Jane", "Smith", new Date(), "456 Oak St", false, trainers3);

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToStringMethod() {
        List<TrainerList> trainers = new ArrayList<>();
        trainers.add(new TrainerList("trainer1", "John", "Doe", "Java"));
        trainers.add(new TrainerList("trainer2", "Jane", "Smith", "Python"));

        UpdateTraineeProfileResponse response = new UpdateTraineeProfileResponse("john.doe", "John", "Doe", new Date(), "123 Main St", true, trainers);

        assertEquals("UpdateTraineeProfileResponse[username=john.doe, firstName=John, lastName=Doe, dateOfBirth=" + response.dateOfBirth() +
                ", address=123 Main St, isActive=true, trainers=" + trainers + "]", response.toString());
    }
}

