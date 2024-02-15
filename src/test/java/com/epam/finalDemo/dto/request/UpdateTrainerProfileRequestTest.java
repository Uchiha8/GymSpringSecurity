package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateTrainerProfileRequestTest {

    @Test
    public void testRecordEquality() {
        UpdateTrainerProfileRequest request1 = new UpdateTrainerProfileRequest("trainer1", "John", "Doe", "Java", true);
        UpdateTrainerProfileRequest request2 = new UpdateTrainerProfileRequest("trainer1", "John", "Doe", "Java", true);
        UpdateTrainerProfileRequest request3 = new UpdateTrainerProfileRequest("trainer2", "Jane", "Smith", "Python", false);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        UpdateTrainerProfileRequest request = new UpdateTrainerProfileRequest("trainer1", "John", "Doe", "Java", true);

        assertEquals("UpdateTrainerProfileRequest[username=trainer1, firstName=John, lastName=Doe, trainingType=Java, isActive=true]", request.toString());
    }
}
