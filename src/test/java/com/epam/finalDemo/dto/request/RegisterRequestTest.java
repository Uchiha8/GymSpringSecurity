package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RegisterRequestTest {
    @Test
    public void testRecordEquality() {
        RegisterRequest request1 = new RegisterRequest("John", "Doe", "john.doe", "password", true, "USER");
        RegisterRequest request2 = new RegisterRequest("John", "Doe", "john.doe", "password", true, "USER");
        RegisterRequest request3 = new RegisterRequest("Jane", "Smith", "jane.smith", "pass123", true, "ADMIN");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "john.doe", "password", true, "USER");

        assertEquals("RegisterRequest[firstName=John, lastName=Doe, username=john.doe, password=password, isActive=true, role=USER]", request.toString());
    }
}
