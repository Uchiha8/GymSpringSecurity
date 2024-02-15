package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrainerRegistrationRequestTest {

    @Test
    public void testRecordEquality() {
        TrainerRegistrationRequest request1 = new TrainerRegistrationRequest("John", "Doe", "Java");
        TrainerRegistrationRequest request2 = new TrainerRegistrationRequest("John", "Doe", "Java");
        TrainerRegistrationRequest request3 = new TrainerRegistrationRequest("Jane", "Smith", "Python");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        TrainerRegistrationRequest request = new TrainerRegistrationRequest("John", "Doe", "Java");

        assertEquals("TrainerRegistrationRequest[firstName=John, lastName=Doe, trainingType=Java]", request.toString());
    }
}
