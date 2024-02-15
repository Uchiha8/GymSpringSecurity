package com.epam.finalDemo.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class TraineeRegistrationRequestTest {
    @Test
    public void testRecordEquality() {
        Date dateOfBirth = new Date();

        TraineeRegistrationRequest request1 = new TraineeRegistrationRequest("John", "Doe", dateOfBirth, "123 Main St");
        TraineeRegistrationRequest request2 = new TraineeRegistrationRequest("John", "Doe", dateOfBirth, "123 Main St");
        TraineeRegistrationRequest request3 = new TraineeRegistrationRequest("Jane", "Smith", new Date(), "456 Oak St");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        Date dateOfBirth = new Date();

        TraineeRegistrationRequest request = new TraineeRegistrationRequest("John", "Doe", dateOfBirth, "123 Main St");

        assertEquals("TraineeRegistrationRequest[firstName=John, lastName=Doe, dateOfBirth=" + dateOfBirth + ", address=123 Main St]", request.toString());
    }
}
