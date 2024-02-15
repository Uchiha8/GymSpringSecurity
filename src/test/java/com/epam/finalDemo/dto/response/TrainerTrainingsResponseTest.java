package com.epam.finalDemo.dto.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Date;

public class TrainerTrainingsResponseTest {

    @Test
    public void testRecordEquality() {
        TrainerTrainingsResponse training1 = new TrainerTrainingsResponse("Java Training", new Date(), "Programming", Duration.ofHours(2), "Trainee1");
        TrainerTrainingsResponse training2 = new TrainerTrainingsResponse("Java Training", new Date(), "Programming", Duration.ofHours(2), "Trainee1");
        TrainerTrainingsResponse training3 = new TrainerTrainingsResponse("Python Training", new Date(), "Programming", Duration.ofHours(3), "Trainee2");

        assertEquals(training1, training2);
        assertNotEquals(training1, training3);
    }

    @Test
    public void testToStringMethod() {
        TrainerTrainingsResponse training = new TrainerTrainingsResponse("Java Training", new Date(), "Programming", Duration.ofHours(2), "Trainee1");

        assertEquals("TrainerTrainingsResponse[trainingName=Java Training, trainingDate=" + training.trainingDate() +
                ", trainingType=Programming, duration=PT2H, traineeName=Trainee1]", training.toString());
    }
}

