package com.epam.finalDemo.dto.response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TrainerProfileResponseTest {

    @Test
    public void testRecordEquality() {
        List<TraineeList> trainees1 = new ArrayList<>();
        trainees1.add(new TraineeList("trainee1", "John", "Doe"));
        trainees1.add(new TraineeList("trainee2", "Jane", "Smith"));

        List<TraineeList> trainees2 = new ArrayList<>();
        trainees2.add(new TraineeList("trainee1", "John", "Doe"));
        trainees2.add(new TraineeList("trainee2", "Jane", "Smith"));

        List<TraineeList> trainees3 = new ArrayList<>();
        trainees3.add(new TraineeList("trainee3", "Bob", "Johnson"));

        TrainerProfileResponse response1 = new TrainerProfileResponse("John", "Doe", "Java", true, trainees1);
        TrainerProfileResponse response2 = new TrainerProfileResponse("John", "Doe", "Java", true, trainees2);
        TrainerProfileResponse response3 = new TrainerProfileResponse("Alice", "Smith", "Python", false, trainees3);

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToStringMethod() {
        List<TraineeList> trainees = new ArrayList<>();
        trainees.add(new TraineeList("trainee1", "John", "Doe"));
        trainees.add(new TraineeList("trainee2", "Jane", "Smith"));

        TrainerProfileResponse response = new TrainerProfileResponse("John", "Doe", "Java", true, trainees);

        assertEquals("TrainerProfileResponse[fistName=John, lastName=Doe, trainingType=Java, isActive=true, trainees=" + trainees + "]", response.toString());
    }
}

