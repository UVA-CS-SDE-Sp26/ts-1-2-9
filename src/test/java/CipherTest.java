import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CipherTest {

    @Test
    void encrypt_decrypt_returnsOriginalText() {
        String originalText = "Hello, World!";
        String encryptedText = Cipher.decrypt(originalText,);
        String decryptedText = Cipher.decrypt(encryptedText);

    }

    @Test
    public void testBasicDecryption() {
        // Z(encoded) from line 2 should return A(decoded) from line 1
        String input = "B";
        String expected = "A";
        String actual = Cipher.decrypt(input);
        assertEquals( expected, actual,"Single letter decryption failed");
    }

    @Test
    public void testDecryptionWithSpaces() {
        // Z Z(encoded) should return A A(decoded) and spaces should be unchanged
        String input = "B B";
        String expected = "A A";
        String actual = Cipher.decrypt(input);
        assertEquals(expected, actual, "Decryption with spaces failed");
    }

    @Test
    public void testUnknownCharacter() {
        // '!' is not in the key file, so it should remain unchanged
        String input = "B!";
        String expected = "A!";
        String actual = Cipher.decrypt(input);
        assertEquals(expected, actual, "Unknown characters should be ignored");
    }
}