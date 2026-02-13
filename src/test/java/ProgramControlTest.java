import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramControlTest {

    private static final List<String> TEST_FILES = List.of("file1.txt", "file2.txt", "file3.txt");
    private static final String FILE_CONTENT_1 = "test file 1 content";
    private static final String FILE_CONTENT_2 = "file 2";
    private static final String DECRYPTED_CONTENT = "decrypted";

    private ProgramControl pc;

    @BeforeEach
    void setUp() {
        pc = new ProgramControl();
    }

    @Test
    void start_withNoArgs_listsAllFiles() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);

            String output = pc.start();

            assertTrue(output.contains("file1.txt"));
            assertTrue(output.contains("file2.txt"));
            assertTrue(output.contains("file3.txt"));
        }
    }

    @Test
    void start_withNoArgs_includesFileNumbers() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);

            String output = pc.start();

            assertTrue(output.contains("01 file1.txt"));
            assertTrue(output.contains("02 file2.txt"));
            assertTrue(output.contains("03 file3.txt"));
        }
    }

    @Test
    void start_emptyDataFolder_returnsNoFilesFound() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(List.of());

            String output = pc.start();

            assertEquals("No files found.", output);
        }
    }

    @Test
    void start_dataFolderDoesNotExist_returnsNoFilesFound() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(null);

            String output = pc.start();

            assertEquals("No files found.", output);
        }
    }

    @Test
    void start_withValidFileNumber_callsDecryptWithDefaultKey() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class);
             MockedStatic<Cipher> mockedCipher = mockStatic(Cipher.class)) {

            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);
            mockedFileHandler.when(() -> FileHandler.checkFile("data", "file1.txt"))
                .thenReturn(true);
            mockedFileHandler.when(() -> FileHandler.readFile("data", "file1.txt"))
                .thenReturn(FILE_CONTENT_1);
            mockedCipher.when(() -> Cipher.decrypt(FILE_CONTENT_1, "key.txt"))
                .thenReturn(DECRYPTED_CONTENT);

            String output = pc.start("1");

            assertEquals(DECRYPTED_CONTENT, output);
            mockedCipher.verify(() -> Cipher.decrypt(FILE_CONTENT_1, "key.txt"));
        }
    }

    @Test
    void start_withInvalidFileNumber_returnsError() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);

            String output = pc.start("999");

            assertEquals("Invalid file number.", output);
        }
    }

    @Test
    void start_withNonNumber_returnsError() {
        String output = pc.start("abc");
        assertEquals("Invalid file number.", output);
    }

    @Test
    void start_withNegativeNumber_returnsError() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);

            String output = pc.start("-1");

            assertEquals("Invalid file number.", output);
        }
    }

    @Test
    void start_withZero_returnsError() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);

            String output = pc.start("0");

            assertEquals("Invalid file number.", output);
        }
    }

    @Test
    void start_noFilesAvailable_returnsNoFilesFound() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(null);

            String output = pc.start("1");

            assertEquals("No files found.", output);
        }
    }

    @Test
    void start_withTwoArgs_callsDecryptWithSpecifiedKey() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class);
             MockedStatic<Cipher> mockedCipher = mockStatic(Cipher.class)) {

            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);
            mockedFileHandler.when(() -> FileHandler.checkFile("data", "file1.txt"))
                .thenReturn(true);
            mockedFileHandler.when(() -> FileHandler.readFile("data", "file1.txt"))
                .thenReturn(FILE_CONTENT_1);
            mockedCipher.when(() -> Cipher.decrypt(FILE_CONTENT_1, "custom_key.txt"))
                .thenReturn(DECRYPTED_CONTENT);

            String output = pc.start("1", "custom_key.txt");

            assertEquals(DECRYPTED_CONTENT, output);
            mockedCipher.verify(() -> Cipher.decrypt(FILE_CONTENT_1, "custom_key.txt"));
        }
    }

    @Test
    void start_withTwoArgs_validFileNumber2_readsCorrectFile() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class);
             MockedStatic<Cipher> mockedCipher = mockStatic(Cipher.class)) {

            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);
            mockedFileHandler.when(() -> FileHandler.checkFile("data", "file2.txt"))
                .thenReturn(true);
            mockedFileHandler.when(() -> FileHandler.readFile("data", "file2.txt"))
                .thenReturn(FILE_CONTENT_2);
            mockedCipher.when(() -> Cipher.decrypt(FILE_CONTENT_2, "key.txt"))
                .thenReturn("decrypted file 2");

            String output = pc.start("2", "key.txt");

            assertEquals("decrypted file 2", output);
            mockedCipher.verify(() -> Cipher.decrypt(FILE_CONTENT_2, "key.txt"));
        }
    }

    @Test
    void start_withTwoArgs_invalidFileNumber_returnsError() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);

            String output = pc.start("999", "key.txt");

            assertEquals("Invalid file number.", output);
        }
    }

    @Test
    void start_withTwoArgs_nonNumericInput_returnsError() {
        String output = pc.start("xyz", "key.txt");
        assertEquals("Invalid file number.", output);
    }

    @Test
    void start_withTwoArgs_fileCannotBeRead_returnsError() {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler.when(() -> FileHandler.listFiles("data"))
                .thenReturn(TEST_FILES);
            mockedFileHandler.when(() -> FileHandler.checkFile("data", "file1.txt"))
                .thenReturn(true);
            mockedFileHandler.when(() -> FileHandler.readFile("data", "file1.txt"))
                .thenReturn(null);

            String output = pc.start("1", "key.txt");

            assertEquals("Error reading file.", output);
        }
    }
}