import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ProgramControlTest {

    private static final Path DATA_DIR = Path.of("data");
    private static final String TEST_FILE_NAME = "test_file_pc.txt";
    private static final String TEST_FILE_CONTENT = "this is a test file for programcontrol.";

    private ProgramControl pc;

    @BeforeAll
    static void setupFolder() throws IOException {
        // create data folder and a test file
        Files.createDirectories(DATA_DIR);
        Files.writeString(DATA_DIR.resolve(TEST_FILE_NAME), TEST_FILE_CONTENT);
    }

    @AfterAll
    static void cleanup() throws IOException {
        // delete the test file after all tests
        Files.deleteIfExists(DATA_DIR.resolve(TEST_FILE_NAME));
    }

    @BeforeEach
    void init() {
        // make a new programcontrol before each test
        pc = new ProgramControl();
    }

    @Test
    void list_files_should_include_test_file() {
        // calling start() with no args should list the test file
        String output = pc.start();
        assertTrue(output.contains(TEST_FILE_NAME), "should list our test file");
    }

    @Test
    void show_file_by_number_should_return_content() {
        // calling start("1") should return the contents of the first file
        String output = pc.start("1");
        assertEquals(TEST_FILE_CONTENT, output, "should return file contents");
    }

    @Test
    void invalid_number_should_return_error() {
        // using a number that doesn't exist should give an error
        String output = pc.start("999");
        assertEquals("invalid file number.", output.toLowerCase());
    }

    @Test
    void non_number_input_should_return_error() {
        // non-numeric input should give an error too
        String output = pc.start("abc");
        assertEquals("invalid file number.", output.toLowerCase());
    }

    @Test
    void two_args_should_try_to_decrypt() {
        // passing two args should attempt to decrypt
        String output = pc.start("1", "dummyKey");
        assertNotNull(output, "decrypted output should not be null");
    }
}