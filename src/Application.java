import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.awt.*;
import java.util.HashSet;
import javafx.animation.*;


public class Application {


    final static double WIN_HEIGHT = 400;
    final static double WIN_WIDTH = 600;

    static HashSet<double[]> mouseClicks = new HashSet<>();


    public static void main(String args[]) {
        JFXPanel panel = new JFXPanel();
        Platform.runLater(()-> start());
    }


    private static void start() {
        System.out.println("Application starting...");
        System.out.println("Initializing Screen Controller");

        ScreenController controller = new ScreenController();

        Group root = new Group();
        Stage mainStage =  new Stage();
        Scene mainScene = new Scene(root);
        Canvas canvas = new Canvas();

        mainStage.setResizable(false);
        mainStage.setHeight(WIN_HEIGHT);
        mainStage.setWidth(WIN_WIDTH);
        mainStage.setTitle("3D Nodes");
        mainStage.setScene(mainScene);
        mainStage.show();

        mainStage.setOnCloseRequest(event -> Application.close());

        canvas.setWidth(WIN_WIDTH);
        canvas.setHeight(WIN_HEIGHT);
        GraphicsContext GC = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(event ->
                mouseClicks.add(new double[] {event.getX(), event.getY()}));

        root.getChildren().add(canvas);
        GC.setFill(Color.BLACK);



    }

    private static void close() {
        System.out.println("Application closing");
        for (double[] d : mouseClicks) {
            System.out.println(d[0] +" "+ d[1]);
        }
        System.exit(0);
    }
}
