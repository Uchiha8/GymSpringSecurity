package com.epam.finalDemo.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Date;

public class TraineeTrainingsResponseTest {

    @Test
    public void testRecordEquality() {
        Date date = new Date();
        TraineeTrainingsResponse training1 = new TraineeTrainingsResponse("Java Training", date, "Programming", Duration.ofHours(2), "Trainer1");
        TraineeTrainingsResponse training2 = new TraineeTrainingsResponse("Java Training", date, "Programming", Duration.ofHours(2), "Trainer1");
        TraineeTrainingsResponse training3 = new TraineeTrainingsResponse("Python Training", date, "Programming", Duration.ofHours(3), "Trainer2");

        assertEquals(training1, training2);
        assertNotEquals(training1, training3);
    }

    @Test
    public void testToStringMethod() {
        TraineeTrainingsResponse training = new TraineeTrainingsResponse("Java Training", new Date(), "Programming", Duration.ofHours(2), "Trainer1");

        assertEquals("TraineeTrainingsResponse[trainingName=Java Training, trainingDate=" + training.trainingDate() +
                ", trainingType=Programming, duration=PT2H, trainerName=Trainer1]", training.toString());
    }
}

