package com.epam.finalDemo.dto.response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrainerListTest {

    @Test
    public void testRecordEquality() {
        TrainerList trainer1 = new TrainerList("trainer1", "John", "Doe", "Java");
        TrainerList trainer2 = new TrainerList("trainer1", "John", "Doe", "Java");
        TrainerList trainer3 = new TrainerList("trainer2", "Jane", "Smith", "Python");

        assertEquals(trainer1, trainer2);
        assertNotEquals(trainer1, trainer3);
    }

    @Test
    public void testToStringMethod() {
        TrainerList trainer = new TrainerList("trainer1", "John", "Doe", "Java");

        assertEquals("TrainerList[username=trainer1, firstName=John, lastName=Doe, trainingType=Java]", trainer.toString());
    }
}

