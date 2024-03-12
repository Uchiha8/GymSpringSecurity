package com.epam.finalDemo.dto.request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class ActionTypeTest {
    @Test
    void testEnumValues() {
        // Arrange
        ActionType create = ActionType.CREATE;
        ActionType delete = ActionType.DELETE;

        // Act & Assert
        assertEquals("CREATE", create.name());
        assertEquals("DELETE", delete.name());

        assertEquals(ActionType.CREATE, ActionType.valueOf("CREATE"));
        assertEquals(ActionType.DELETE, ActionType.valueOf("DELETE"));

        assertEquals(2, ActionType.values().length);
        assertEquals(create, ActionType.values()[0]);
        assertEquals(delete, ActionType.values()[1]);
    }
}
