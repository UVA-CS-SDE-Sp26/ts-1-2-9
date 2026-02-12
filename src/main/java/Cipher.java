import java.util.HashMap;
import java.util.Map;
public class Cipher {
    private static Map<Character, Character> decodingMap = null;
    private static final String KEY_FILE = "key.txt";

    public static String decrypt(String input) {
        String keyContent = FileHandler.readFile(KEY_FILE);
        if (keyContent == null || keyContent.isEmpty()) {
            return input; // Return input as is if the key file is missing or empty
        }
        String[] lines = keyContent.split("\n");
        if (lines.length < 2) {
            return input;
        }
        String decodedChars = lines[0].trim();
        String  encodedChars= lines[1].trim();
        String result = "";
        for (char currentLetter : input.toCharArray()) {

            // Find where this letter exists in the 'cipherRow'
            int index = encodedChars.indexOf(currentLetter);

            // If we found the letter in the cipher row (index is not -1)
            if (index != -1) {
                // Pick the corresponding letter from the 'plainRow'
                char plainLetter = decodedChars.charAt(index);
                result += plainLetter;
            } else {
                // If the letter isn't in the key (like a space or punctuation), keep it as is
                result += currentLetter;
            }
        }

        return result.toString();


    }
}
