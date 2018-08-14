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
import java.util.ArrayList;
import java.util.HashSet;
import javafx.animation.*;


public class Application {


    final static double WIN_HEIGHT = 400;
    final static double WIN_WIDTH = 600;

    final static double[] defaultSpeed = {5,0,5};
    final static double defaultSize = 10;
    final static double defaultSizeM = 1;

    static HashSet<double[]> mouseClicks = new HashSet<>();
    static ArrayList<Node> Nodes = new ArrayList<>();


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
                mouseClicks.add(new double[] {event.getX(), event.getY(), 0}));

        root.getChildren().add(canvas);
        GC.setFill(Color.BLACK);
        GC.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                GC.setStroke(Color.WHITE);
                GC.setFill(Color.WHITE);


                if ( Nodes!=null && !Nodes.isEmpty()){
                    System.out.println("Updating Positions");
                    for (Node n : Nodes) {
                        n.updatePosition();
                    }
                }

                if (mouseClicks!=null && !mouseClicks.isEmpty()) {
                    for (double[] pos : mouseClicks) {
                        Nodes.add(new Node(pos, defaultSize, defaultSizeM, defaultSpeed));
                    }
                    mouseClicks.clear();
                }


                if (Nodes!=null && !Nodes.isEmpty()) {
                    GC.setFill(Color.BLACK);
                    GC.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);
                    GC.setFill(Color.WHITE);
                    for (Node n : Nodes) {
                        GC.fillOval(n.position[0], n.position[1], n.size, n.size);
                    }
                }




            }

        }.start();


    }


    private static void close() {
        System.out.println("Application closing");

        /*for (double[] d : mouseClicks) {
            System.out.println(d[0] +" "+ d[1]);
        }*/

        System.exit(0);
    }
}
