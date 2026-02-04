import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class FileHandler {
    /**
     * Checks if a file within the data directory exists and can be read.
     *
     * @param fileName the name of the file to check (relative to data directory)
     * @return true if the file exists and can be read, false otherwise
     */
    public static boolean checkFile(@NonNull String fileName) {
        return false;
    }

    /**
     * Reads the entire contents of a file within the data directory.
     *
     * @param fileName the name of the file to read (relative to data directory)
     * @return the contents of the file, or null if something goes wrong (e.g. file doesn't exist)
     */
    public static @Nullable String readFile(@NonNull String fileName) {
        return null;
    }
}
