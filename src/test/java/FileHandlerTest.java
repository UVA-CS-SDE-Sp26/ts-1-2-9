import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {
    private static final Path DATA_DIR = Path.of("data");
    private static final String TEST_FILE_PATH = "test_file_6789.txt";
    private static final String TEST_FILE_CONTENT = "Hello, World!\nLine 2";
    private static final String NONEXISTENT_FILE_PATH = "nonexistent_file_12345.txt";
    private static final String INACCESSIBLE_FILE_PATH = "inaccessible_file_54321.txt";

    @BeforeAll
    static void setUp() throws IOException {
        Files.createDirectories(DATA_DIR);
        Files.writeString(DATA_DIR.resolve(TEST_FILE_PATH), TEST_FILE_CONTENT);

        Path inaccessibleFile = DATA_DIR.resolve(INACCESSIBLE_FILE_PATH);
        if (Files.notExists(inaccessibleFile)) {
            Files.createFile(inaccessibleFile);
        }
        var setNotReadable = inaccessibleFile.toFile().setReadable(false);
        if (!setNotReadable) {
            throw new IOException("Failed to set file as not readable: " + inaccessibleFile);
        }
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(DATA_DIR.resolve(TEST_FILE_PATH));
        Files.deleteIfExists(DATA_DIR.resolve(INACCESSIBLE_FILE_PATH));
    }

    @Test
    void checkFile_existingFile_returnsTrue() {
        assertTrue(FileHandler.checkFile("data", TEST_FILE_PATH));
    }

    @Test
    void checkFile_nonexistentFile_returnsFalse() {
        assertFalse(FileHandler.checkFile("data", NONEXISTENT_FILE_PATH));
    }

    @Test
    void checkFile_emptyFileName_returnsFalse() {
        assertFalse(FileHandler.checkFile("data", ""));
    }

    @Test
    void checkFile_inaccessibleFile_returnsFalse() {
        assertFalse(FileHandler.checkFile("data", INACCESSIBLE_FILE_PATH));
    }

    @Test
    void readFile_existingFile_returnsContent() {
        String content = FileHandler.readFile("data", TEST_FILE_PATH);
        assertEquals(TEST_FILE_CONTENT, content);
    }

    @Test
    void readFile_nonexistentFile_returnsNull() {
        assertNull(FileHandler.readFile("data", NONEXISTENT_FILE_PATH));
    }

    @Test
    void readFile_emptyFileName_returnsNull() {
        assertNull(FileHandler.readFile("data", ""));
    }

    @Test
    void readFile_inaccessibleFile_returnsNull() {
        assertNull(FileHandler.readFile("data", INACCESSIBLE_FILE_PATH));
    }
}
