package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Date;
public class PostTrainingRequestTest {
    @Test
    public void testRecordEquality() {
        Date trainingDate = new Date();
        Duration duration = Duration.ofHours(2);

        PostTrainingRequest request1 = new PostTrainingRequest("trainee", "trainer", "Java Training", trainingDate, duration);
        PostTrainingRequest request2 = new PostTrainingRequest("trainee", "trainer", "Java Training", trainingDate, duration);
        PostTrainingRequest request3 = new PostTrainingRequest("anotherTrainee", "anotherTrainer", "Python Training", new Date(), Duration.ofHours(3));

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        Date trainingDate = new Date();
        Duration duration = Duration.ofHours(2);

        PostTrainingRequest request = new PostTrainingRequest("trainee", "trainer", "Java Training", trainingDate, duration);

        assertEquals("PostTrainingRequest[traineeUsername=trainee, trainerUsername=trainer, trainingName=Java Training, trainingDate=" + trainingDate + ", duration=" + duration + "]", request.toString());
    }
}
