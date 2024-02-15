package com.epam.finalDemo.dto.response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class UpdateTrainerProfileResponseTest {

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

        UpdateTrainerProfileResponse response1 = new UpdateTrainerProfileResponse("trainer1", "John", "Doe", "Java", true, trainees1);
        UpdateTrainerProfileResponse response2 = new UpdateTrainerProfileResponse("trainer1", "John", "Doe", "Java", true, trainees2);
        UpdateTrainerProfileResponse response3 = new UpdateTrainerProfileResponse("trainer2", "Jane", "Smith", "Python", false, trainees3);

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToStringMethod() {
        List<TraineeList> trainees = new ArrayList<>();
        trainees.add(new TraineeList("trainee1", "John", "Doe"));
        trainees.add(new TraineeList("trainee2", "Jane", "Smith"));

        UpdateTrainerProfileResponse response = new UpdateTrainerProfileResponse("trainer1", "John", "Doe", "Java", true, trainees);

        assertEquals("UpdateTrainerProfileResponse[username=trainer1, firstName=John, lastName=Doe, trainingType=Java, isActive=true, trainees=" + trainees + "]", response.toString());
    }
}

