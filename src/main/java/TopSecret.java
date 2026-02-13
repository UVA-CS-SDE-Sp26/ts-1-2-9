import java.util.logging.FileHandler;

/**
 * Commmand Line Utility
 */
public class TopSecret {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        System.out.println(ui.inputLogic(args));
    }
}
