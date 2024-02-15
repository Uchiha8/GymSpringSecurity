package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AuthenticationRequestTest {
    @Test
    public void testRecordEquality() {
        AuthenticationRequest request1 = new AuthenticationRequest("user", "password");
        AuthenticationRequest request2 = new AuthenticationRequest("user", "password");
        AuthenticationRequest request3 = new AuthenticationRequest("anotherUser", "password");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        AuthenticationRequest request = new AuthenticationRequest("user", "password");

        assertEquals("AuthenticationRequest[username=user, password=password]", request.toString());
    }
}
