package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeStatusRequestTest {

    @Test
    public void testRecordEquality() {
        ChangeStatusRequest request1 = new ChangeStatusRequest("user", true);
        ChangeStatusRequest request2 = new ChangeStatusRequest("user", true);
        ChangeStatusRequest request3 = new ChangeStatusRequest("anotherUser", false);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testToStringMethod() {
        ChangeStatusRequest request = new ChangeStatusRequest("user", true);

        assertEquals("ChangeStatusRequest[username=user, status=true]", request.toString());
    }
}
