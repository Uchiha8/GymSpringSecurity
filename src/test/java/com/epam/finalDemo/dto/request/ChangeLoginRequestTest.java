package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ChangeLoginRequestTest {
    @Test
    public void testRecordEquality() {
        ChangeLoginRequest request1 = new ChangeLoginRequest("user", "oldPassword", "newPassword");
        ChangeLoginRequest request2 = new ChangeLoginRequest("user", "oldPassword", "newPassword");
        ChangeLoginRequest request3 = new ChangeLoginRequest("anotherUser", "oldPassword", "newPassword");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        ChangeLoginRequest request = new ChangeLoginRequest("user", "oldPassword", "newPassword");

        assertEquals("ChangeLoginRequest[username=user, oldPassword=oldPassword, newPassword=newPassword]", request.toString());
    }
}
