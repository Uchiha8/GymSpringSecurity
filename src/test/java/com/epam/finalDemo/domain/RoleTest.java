package com.epam.finalDemo.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RoleTest {
    @Test
    public void testEnumValues() {
        Role userRole = Role.ROLE_USER;
        Role adminRole = Role.ROLE_ADMIN;

        assertEquals("ROLE_USER", userRole.name());
        assertEquals("ROLE_ADMIN", adminRole.name());
    }

    @Test
    public void testEnumToString() {
        Role userRole = Role.ROLE_USER;
        Role adminRole = Role.ROLE_ADMIN;

        assertEquals("ROLE_USER", userRole.toString());
        assertEquals("ROLE_ADMIN", adminRole.toString());
    }

    @Test
    public void testEnumValuesEquality() {
        Role userRole = Role.ROLE_USER;
        Role adminRole = Role.ROLE_ADMIN;

        assertNotEquals(userRole, adminRole);
    }
}
