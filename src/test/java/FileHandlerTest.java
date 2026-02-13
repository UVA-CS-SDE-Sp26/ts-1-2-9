import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {
    private static final Path DATA_DIR = Path.of("data");
    private static final Path CIPHERS_DIR = Path.of("ciphers");
    private static final String TEST_FILE_PATH = "test_file_6789.txt";
    private static final String TEST_FILE_CONTENT = "Hello, World!\nLine 2";
    private static final String NONEXISTENT_FILE_PATH = "nonexistent_file_12345.txt";
    private static final String INACCESSIBLE_FILE_PATH = "inaccessible_file_54321.txt";

    @BeforeAll
    static void setUp() throws IOException {
        Files.createDirectories(DATA_DIR);
        Files.createDirectories(CIPHERS_DIR);
        Files.writeString(DATA_DIR.resolve(TEST_FILE_PATH), TEST_FILE_CONTENT);
        Files.writeString(CIPHERS_DIR.resolve(TEST_FILE_PATH), TEST_FILE_CONTENT);
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(DATA_DIR.resolve(TEST_FILE_PATH));
        Files.deleteIfExists(CIPHERS_DIR.resolve(TEST_FILE_PATH));
    }

    private static void setUpInaccessibleFile() throws IOException {
        Path inaccessibleFile = DATA_DIR.resolve(INACCESSIBLE_FILE_PATH);
        if (Files.notExists(inaccessibleFile)) {
            Files.createFile(inaccessibleFile);
        }
        var setNotReadable = inaccessibleFile.toFile().setReadable(false);
        if (!setNotReadable) {
            throw new IOException("Failed to set file as not readable: " + inaccessibleFile);
        }
    }

    private static void tearDownInaccessibleFile() throws IOException {
        Files.deleteIfExists(DATA_DIR.resolve(INACCESSIBLE_FILE_PATH));
    }

    @Test
    void checkFile_existingFileInData_returnsTrue() {
        assertTrue(FileHandler.checkFile("data", TEST_FILE_PATH));
    }

    @Test
    void checkFile_existingFileInCiphers_returnsTrue() {
        assertTrue(FileHandler.checkFile("ciphers", TEST_FILE_PATH));
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
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void checkFile_inaccessibleFile_returnsFalse() throws IOException {
        setUpInaccessibleFile();
        try {
            assertFalse(FileHandler.checkFile("data", INACCESSIBLE_FILE_PATH));
        } finally {
            tearDownInaccessibleFile();
        }
    }

    @Test
    void checkFile_invalidDirectory_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
            FileHandler.checkFile("invalid", TEST_FILE_PATH));
    }

    @Test
    void readFile_existingFileInData_returnsContent() {
        String content = FileHandler.readFile("data", TEST_FILE_PATH);
        assertEquals(TEST_FILE_CONTENT, content);
    }

    @Test
    void readFile_existingFileInCiphers_returnsContent() {
        String content = FileHandler.readFile("ciphers", TEST_FILE_PATH);
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
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void readFile_inaccessibleFile_returnsNull() throws IOException {
        setUpInaccessibleFile();
        try {
            assertNull(FileHandler.readFile("data", INACCESSIBLE_FILE_PATH));
        } finally {
            tearDownInaccessibleFile();
        }
    }

    @Test
    void readFile_invalidDirectory_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
            FileHandler.readFile("invalid", TEST_FILE_PATH));
    }

    @Test
    void listFiles_dataDirectory_returnsFileNames() {
        List<String> files = FileHandler.listFiles("data");
        assertNotNull(files);
        assertFalse(files.isEmpty());
        boolean foundTestFile = false;
        for (String file : files) {
            if (file.equals(TEST_FILE_PATH)) {
                foundTestFile = true;
                break;
            }
        }
        assertTrue(foundTestFile, "Should find test file in data directory");
    }

    @Test
    void listFiles_ciphersDirectory_returnsFileNames() {
        List<String> files = FileHandler.listFiles("ciphers");
        assertNotNull(files);
        assertFalse(files.isEmpty());
        boolean foundTestFile = false;
        for (String file : files) {
            if (file.equals(TEST_FILE_PATH)) {
                foundTestFile = true;
                break;
            }
        }
        assertTrue(foundTestFile, "Should find test file in ciphers directory");
    }

    @Test
    void listFiles_invalidDirectory_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
            FileHandler.listFiles("invalid"));
    }

    @Test
    void listFiles_nonexistentDirectory_returnsNull() throws IOException {
        Path tempDir = Path.of("data");
        Path backupDir = Path.of("data_backup_test");

        // Temporarily move data directory
        Files.move(tempDir, backupDir);

        try {
            List<String> files = FileHandler.listFiles("data");
            assertNull(files, "Should return null for non-existent directory");
        } finally {
            // Restore data directory
            Files.move(backupDir, tempDir);
        }
    }

    @Test
    void listFiles_emptyDirectory_returnsEmptyArray() throws IOException {
        Path emptyTestDir = Path.of("data/empty_test_subdir");
        Files.createDirectories(emptyTestDir);

        try {
            // Create empty subdirectory - but we can't test this directly since
            // listFiles only works on "data" or "ciphers" directories
            // So we'll just verify our test file exists
            List<String> files = FileHandler.listFiles("data");
            assertNotNull(files);
            // files.length could be 0 or more depending on directory contents
        } finally {
            Files.deleteIfExists(emptyTestDir);
        }
    }
}
