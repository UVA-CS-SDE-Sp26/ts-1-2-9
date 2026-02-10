import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.logging.FileHandler;

import static org.mockito.Mockito.*;
class UserInterfaceTest {
    @Test
    void testNoArguments() {
        // Create a mock object
        ProgramControl mockObject = mock(ProgramControl.class);

        // Define behavior for add method
        when(mockObject.add(2, 3)).thenReturn(100); // fake value for testing

        // Call the method
        int result = mockCalc.add(2, 3);

        // Verify the result
        assertEquals(100, result);

        // Optionally verify the method was called
        verify(mockCalc).add(2, 3);
    }
    void testOneArgument() {
        // Create a mock object
        FileHandler mockCalc = mock(FileHandler.class);

        // Define behavior for add method
        when(mockCalc.add(2, 3)).thenReturn(100); // fake value for testing

        // Call the method
        int result = mockCalc.add(2, 3);




    }
    void testTwoArguments() {
        // Create a mock object
        FileHandler mockCalc = mock(FileHandler.class);

        // Define behavior for add method
        when(mockCalc.add(2, 3)).thenReturn(100); // fake value for testing

        // Call the method
        int result = mockCalc.add(2, 3);

        // Verify the result
        assertEquals(100, result);


    }
}