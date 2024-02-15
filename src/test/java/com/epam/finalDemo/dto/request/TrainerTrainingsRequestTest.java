package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class TrainerTrainingsRequestTest {

    @Test
    public void testRecordEquality() {
        Date periodFrom = new Date();
        Date periodTo = new Date();

        TrainerTrainingsRequest request1 = new TrainerTrainingsRequest("trainer1", periodFrom, periodTo, "Trainee1");
        TrainerTrainingsRequest request2 = new TrainerTrainingsRequest("trainer1", periodFrom, periodTo, "Trainee1");
        TrainerTrainingsRequest request3 = new TrainerTrainingsRequest("trainer2", new Date(), new Date(), "Trainee2");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        Date periodFrom = new Date();
        Date periodTo = new Date();

        TrainerTrainingsRequest request = new TrainerTrainingsRequest("trainer1", periodFrom, periodTo, "Trainee1");

        assertEquals("TrainerTrainingsRequest[username=trainer1, periodFrom=" + periodFrom + ", periodTo=" + periodTo + ", traineeName=Trainee1]", request.toString());
    }
}
