import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {
    private static final Path DATA_DIR = Path.of("data");

    /**
     * Checks if a file exists and can be read.
     *
     * @param directory the directory to check (relative to the project root). Must be "data" or "ciphers".
     * @param fileName  the name of the file to check (relative to directory)
     * @return true if the file exists and can be read, false otherwise
     */
    public static boolean checkFile(@NonNull String directory, @NonNull String fileName) {
        Path filePath = DATA_DIR.resolve(fileName);
        return Files.exists(filePath)
            && Files.isReadable(filePath)
            && Files.isRegularFile(filePath);
    }

    /**
     * Reads the entire contents of a file.
     *
     * @param directory the directory to read from (relative to the project root). Must be "data" or "ciphers".
     * @param fileName  the name of the file to read (relative to directory)
     * @return the contents of the file, or null if something goes wrong (e.g. file doesn't exist)
     */
    public static @Nullable String readFile(@NonNull String directory, @NonNull String fileName) {
        Path filePath = DATA_DIR.resolve(fileName);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            return null;
        }
    }
}
