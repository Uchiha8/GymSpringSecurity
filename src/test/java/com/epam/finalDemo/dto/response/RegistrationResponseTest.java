package com.epam.finalDemo.dto.response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationResponseTest {

    @Test
    public void testBuilder() {
        RegistrationResponse response = RegistrationResponse.builder()
                .username("john.doe")
                .password("securePassword")
                .token("exampleToken")
                .build();

        assertNotNull(response);
        assertEquals("john.doe", response.username());
        assertEquals("securePassword", response.password());
        assertEquals("exampleToken", response.token());
    }

    @Test
    public void testRecordEquality() {
        RegistrationResponse response1 = RegistrationResponse.builder()
                .username("john.doe")
                .password("securePassword")
                .token("exampleToken")
                .build();
        RegistrationResponse response2 = RegistrationResponse.builder()
                .username("john.doe")
                .password("securePassword")
                .token("exampleToken")
                .build();
        RegistrationResponse response3 = RegistrationResponse.builder()
                .username("jane.smith")
                .password("password123")
                .token("anotherToken")
                .build();

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
    }

    @Test
    public void testToStringMethod() {
        RegistrationResponse response = RegistrationResponse.builder()
                .username("john.doe")
                .password("securePassword")
                .token("exampleToken")
                .build();

        assertEquals("RegistrationResponse[username=john.doe, password=securePassword, token=exampleToken]", response.toString());
    }
}
