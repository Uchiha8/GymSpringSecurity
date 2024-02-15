package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
public class TraineeTrainingsRequestTest {
    @Test
    public void testRecordEquality() {
        Date periodFrom = new Date();
        Date periodTo = new Date();

        TraineeTrainingsRequest request1 = new TraineeTrainingsRequest("john.doe", periodFrom, periodTo, "Trainer1", "Java");
        TraineeTrainingsRequest request2 = new TraineeTrainingsRequest("john.doe", periodFrom, periodTo, "Trainer1", "Java");
        TraineeTrainingsRequest request3 = new TraineeTrainingsRequest("jane.smith", new Date(), new Date(), "Trainer2", "Python");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        Date periodFrom = new Date();
        Date periodTo = new Date();

        TraineeTrainingsRequest request = new TraineeTrainingsRequest("john.doe", periodFrom, periodTo, "Trainer1", "Java");

        assertEquals("TraineeTrainingsRequest[username=john.doe, periodFrom=" + periodFrom + ", periodTo=" + periodTo + ", trainerName=Trainer1, trainingType=Java]", request.toString());
    }
}
