package com.epam.finalDemo.dto.response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TraineeListTest {
    @Test
    public void testRecordEquality() {
        TraineeList trainee1 = new TraineeList("john.doe", "John", "Doe");
        TraineeList trainee2 = new TraineeList("john.doe", "John", "Doe");
        TraineeList trainee3 = new TraineeList("jane.smith", "Jane", "Smith");

        assertEquals(trainee1, trainee2);
        assertNotEquals(trainee1, trainee3);
    }

    @Test
    public void testToStringMethod() {
        TraineeList trainee = new TraineeList("john.doe", "John", "Doe");

        assertEquals("TraineeList[username=john.doe, firstName=John, lastName=Doe]", trainee.toString());
    }
}
