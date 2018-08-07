import javafx.scene.*;
import javafx.event.*;
import javafx.stage.*;

import java.util.logging.Handler;

public class Nodes3D {

    public static void main(String[] args) {
        ScreenController controller = new ScreenController();

        ScreenController.launch(ScreenController.class, args);

        NodeList currentNodes = new NodeList();

        boolean running = true;

        while (running) {
            controller.updateFrame(currentNodes);
            //need to check for other things eg inputs here so as not to get stuck with the.
        }
    }
}
