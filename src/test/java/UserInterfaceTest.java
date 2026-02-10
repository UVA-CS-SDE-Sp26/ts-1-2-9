import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserInterfaceTest {

    @Test
    void testInvalidArguments() {
        // Capture system output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // too many arguments
        String[] args = {"arg0", "arg1", "arg2", "arg3"};
        new UserInterface(args); // constructs ProgramControl internally

        String output = outContent.toString();
        assertTrue(output.contains("Invalid number of command line arguments"));

        // Reset output
        System.setOut(System.out);
    }
}
