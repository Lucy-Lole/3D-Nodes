import javafx.application.Application;
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.*;

public class Nodes3D extends Application {

    private Stage stage;

    @Override
    public void start(Stage mainStage) throws Exception{
        ScreenController controller = new ScreenController();

        controller.initialize();

        System.out.println("Screen Controller launched!");

        NodeList currentNodes = new NodeList();

        boolean running = true;

        double[] testPos = {1,1,1};
        double[] testSpeed = {1,1,1};

        currentNodes.AddNode(testPos,1,1,testSpeed);
        System.out.println("Test Node created!");

        while (running) {
            controller.updateFrame(currentNodes);
            //need to check for other things eg inputs here so as not to get stuck with the.
        }
    }
}
