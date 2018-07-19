import javafx.scene.*;
import javafx.event.*;
import javafx.stage.*;

import java.util.logging.Handler;

public class Nodes3D {

    Handler handler

    public static void main(String[] args) {
        ScreenController controller = new ScreenController();

        ScreenController.launch(ScreenController.class, args);

        Node[] nodes = {};

        boolean running = true;

        while (running) {
            controller.updateFrame(nodes);


        }


    }

    public static void createNode(int x,int y,Node[] nodes) {



    }


}
