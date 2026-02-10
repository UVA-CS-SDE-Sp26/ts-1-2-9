import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {
    private static final Path DATA_DIR = Path.of("data");

    /**
     * Checks if a file within the data directory exists and can be read.
     *
     * @param fileName the name of the file to check (relative to data directory)
     * @return true if the file exists and can be read, false otherwise
     */
    public static boolean checkFile(@NonNull String fileName) {
        Path filePath = DATA_DIR.resolve(fileName);
        return Files.exists(filePath)
            && Files.isReadable(filePath)
            && Files.isRegularFile(filePath);
    }

    /**
     * Reads the entire contents of a file within the data directory.
     *
     * @param fileName the name of the file to read (relative to data directory)
     * @return the contents of the file, or null if something goes wrong (e.g. file doesn't exist)
     */
    public static @Nullable String readFile(@NonNull String fileName) {
        Path filePath = DATA_DIR.resolve(fileName);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            return null;
        }
    }
}
