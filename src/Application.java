import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.*;
import javafx.stage.*;
import java.util.HashSet;
import javafx.animation.*;


public class Application {


    static HashSet<KeyCode> keysPressed = new HashSet<>();




    public static void main(String args[]) {
        JFXPanel panel = new JFXPanel();
        Platform.runLater(()-> start());
    }


    private static void start() {
        
    }
}
