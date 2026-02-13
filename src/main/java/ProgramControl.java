import java.io.File;
public class ProgramControl {


    public ProgramControl() {
    }

    // no arguments just list files
    public String start() {
        File folder = new File("data");  // use "data" folder
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            return "No files found.";
        }

        String result = "";
        for (int i = 0; i < files.length; i++) {
            String number = String.format("%02d", i + 1);
            result += number + " " + files[i].getName() + "\n";
        }

        return result;
    }
        //one argument means you display the file
        public String start(String arg){
            File folder = new File("data");
            File[] files = folder.listFiles();

            if (files == null || files.length == 0) {
                return "No files found.";
            }

            int index;
            try {
                index = Integer.parseInt(arg) - 1;
            } catch (NumberFormatException e) {
                return "Invalid file number.";
            }

            if (index < 0 || index >= files.length) {
                return "Invalid file number.";
            }

            String content = FileHandler.readFile("data", files[index].getName());
            if (content == null) {
                return "Error reading file.";
            }

            return content;
        }

        //two arguments means decrypt the file
        public String start(String arg1, String arg2){
            String content = start(arg1);

            // Return error messages early
            if (content.startsWith("Invalid") || content.startsWith("Error") || content.equals("No files found.")) {
                return content;
            }

            // Decrypt content using Cipher
            return Cipher.decrypt(content);

        }
    }
