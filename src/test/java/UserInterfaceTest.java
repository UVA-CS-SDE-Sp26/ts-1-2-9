import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserInterfaceTest {

    private ProgramControl mockPc;

    @BeforeEach
    void setUp() {
        mockPc = Mockito.mock(ProgramControl.class);
    }

    @Test
    void test_oneArgumentCall() {
//        when(mockPc.start()).thenReturn("started");

        String[] args = {"TopSecret"};
        new UserInterface(mockPc, args);

        verify(mockPc, times(1)).start();
    }

    @Test
    void test_twoArgumentsCall() {
//        when(mockPc.start("01")).thenReturn("started1");

        String[] args = {"TopSecret", "01"};
        new UserInterface(mockPc, args);

        verify(mockPc, times(1)).start("01");
    }

    @Test
    void test_threeArgumentsCall() {
//        when(mockPc.start("01", "02")).thenReturn("started2");

        String[] args = {"TopSecret", "01", "02"};
        new UserInterface(mockPc, args);

        verify(mockPc, times(1)).start("01", "02");
    }

    @Test
    void test_invalidArguments_returnsErrorMessage() {
        String[] args = {"TopSecret", "b", "c", "d"};
        UserInterface ui = new UserInterface(mockPc, args);

        String result = ui.inputLogic(args);

        assertEquals(
                "Invalid number of command line arguments. Please refer to userinterface.txt for valid commands",
                result
        );
    }
}
