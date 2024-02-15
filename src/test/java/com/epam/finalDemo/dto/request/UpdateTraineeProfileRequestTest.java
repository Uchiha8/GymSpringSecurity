package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class UpdateTraineeProfileRequestTest {

    @Test
    public void testRecordEquality() {
        Date dateOfBirth = new Date();

        UpdateTraineeProfileRequest request1 = new UpdateTraineeProfileRequest("john.doe", "John", "Doe", dateOfBirth, "123 Main St", true);
        UpdateTraineeProfileRequest request2 = new UpdateTraineeProfileRequest("john.doe", "John", "Doe", dateOfBirth, "123 Main St", true);
        UpdateTraineeProfileRequest request3 = new UpdateTraineeProfileRequest("jane.smith", "Jane", "Smith", new Date(), "456 Oak St", false);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        Date dateOfBirth = new Date();

        UpdateTraineeProfileRequest request = new UpdateTraineeProfileRequest("john.doe", "John", "Doe", dateOfBirth, "123 Main St", true);

        assertEquals("UpdateTraineeProfileRequest[username=john.doe, firstName=John, lastName=Doe, dateOfBirth=" + dateOfBirth + ", address=123 Main St, isActive=true]", request.toString());
    }
}
