import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileHandler {

    /**
     * Checks if a file exists and can be read.
     *
     * @param directory the directory to check (relative to the project root). Must be "data" or "ciphers".
     * @param fileName  the name of the file to check (relative to directory)
     * @return true if the file exists and can be read, false otherwise
     */
    public static boolean checkFile(@NonNull String directory, @NonNull String fileName) {
        if (!directory.equalsIgnoreCase("data")
            && !directory.equalsIgnoreCase("ciphers")) {
            throw new IllegalArgumentException("Directory must be 'data' or 'ciphers'");
        }
        Path filePath = Path.of(directory).resolve(fileName);
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
        if (!directory.equalsIgnoreCase("data")
            && !directory.equalsIgnoreCase("ciphers")) {
            throw new IllegalArgumentException("Directory must be 'data' or 'ciphers'");
        }
        Path filePath = Path.of(directory).resolve(fileName);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Lists all regular files in a directory.
     *
     * @param directory the directory to list (relative to the project root). Must be "data" or "ciphers".
     * @return a list of file names, or null if the directory doesn't exist or can't be read
     */
    public static @Nullable List<String> listFiles(@NonNull String directory) {
        if (!directory.equalsIgnoreCase("data")
            && !directory.equalsIgnoreCase("ciphers")) {
            throw new IllegalArgumentException("Directory must be 'data' or 'ciphers'");
        }
        Path dirPath = Path.of(directory);
        try (Stream<Path> stream = Files.list(dirPath)) {
            return stream
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .toList();
        } catch (IOException e) {
            return null;
        }
    }
}
