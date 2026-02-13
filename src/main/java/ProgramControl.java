import org.jspecify.annotations.NonNull;

import java.util.List;

public class ProgramControl {

    /**
     * Lists all files in the data directory with corresponding numbers.
     *
     * @return a numbered list of files, or "No files found." if the directory is empty
     */
    public String start() {
        List<String> files = FileHandler.listFiles("data");

        if (files == null || files.isEmpty()) {
            return "No files found.";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < files.size(); i++) {
            String number = String.format("%02d", i + 1);
            result.append(number).append(" ").append(files.get(i)).append("\n");
        }

        return result.toString();
    }

    /**
     * Reads a file from the data directory by its number and decrypts it using the specified cipher key.
     *
     * @param arg  the file number (1-based index)
     * @param path the cipher key file name (from the ciphers directory)
     * @return the decrypted file contents, or an error message if the file number is invalid
     */
    public String start(@NonNull String arg, @NonNull String path) {
        List<String> files = FileHandler.listFiles("data");

        if (files == null || files.isEmpty()) {
            return "No files found.";
        }

        int index;
        try {
            // subtract 1 to convert to a 0-based index
            index = Integer.parseInt(arg) - 1;
        } catch (NumberFormatException e) {
            return "Invalid file number.";
        }

        if (index < 0 || index >= files.size()) {
            return "Invalid file number.";
        }

        String fileName = files.get(index);
        if (!FileHandler.checkFile("data", fileName)) {
            return "Error reading file.";
        }

        String content = FileHandler.readFile("data", fileName);
        if (content == null) {
            return "Error reading file.";
        }

        // Decrypt content using Cipher
        return Cipher.decrypt(content, path);
    }

    /**
     * Reads a file from the data directory by its number and decrypts it using the default cipher key (key.txt).
     *
     * @param arg1 the file number (1-based index)
     * @return the decrypted file contents, or an error message if the file number is invalid
     */
    public String start(@NonNull String arg1) {
        return start(arg1, "key.txt");
    }
}
