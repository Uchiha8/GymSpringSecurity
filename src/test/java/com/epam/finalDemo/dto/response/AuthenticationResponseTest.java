package com.epam.finalDemo.dto.response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AuthenticationResponseTest {
    @Test
    public void testBuilder() {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("exampleToken")
                .build();

        assertNotNull(response);
        assertEquals("exampleToken", response.token());
    }

    @Test
    public void testRecordEquality() {
        AuthenticationResponse response1 = AuthenticationResponse.builder().token("token1").build();
        AuthenticationResponse response2 = AuthenticationResponse.builder().token("token1").build();
        AuthenticationResponse response3 = AuthenticationResponse.builder().token("token2").build();

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToStringMethod() {
        AuthenticationResponse response = AuthenticationResponse.builder().token("exampleToken").build();

        assertEquals("AuthenticationResponse[token=exampleToken]", response.toString());
    }
}
