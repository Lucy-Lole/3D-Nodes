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
import java.util.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.animation.*;


public class Application {


    final static double WIN_HEIGHT = 800;
    final static double WIN_WIDTH = 1200;

    final static double[] defaultSpeed = {3,3,5};
    final static double defaultSize = 10;
    final static double defaultSizeM = 1;

    static double[] boundaries;

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

        mainStage.setResizable(true);
        mainStage.setHeight(WIN_HEIGHT);
        mainStage.setWidth(WIN_WIDTH);
        mainStage.setTitle("3D Nodes");
        mainStage.setScene(mainScene);
        canvas.setWidth(mainStage.getWidth());
        canvas.setHeight(mainStage.getHeight());
        GraphicsContext GC = canvas.getGraphicsContext2D();



        //There seems to be some weirdness where the canvas and stage can't agree on the same
        //size, so i've manually found the size difference and fixed it
        double [] boundaries = {canvas.getWidth()-17,canvas.getHeight()-40,400};

        mainStage.show();


        mainStage.setOnCloseRequest(event -> Application.close());





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


                //UPDATING NODES POSITIONS
                if ( Nodes!=null && !Nodes.isEmpty()){
                    for (Node n : Nodes) {
                        n.updatePosition(boundaries);
                    }
                }


                //CREATING NEW NODES IF MOUSE IS CLICKED
                if (mouseClicks!=null && !mouseClicks.isEmpty()) {
                    Random randomGen = new Random();
                    for (double[] pos : mouseClicks) {
                        Nodes.add(new Node(pos, defaultSize, defaultSizeM,
                                new double[] {
                                        randomGen.nextDouble()*6-3,
                                        randomGen.nextDouble()*6-3,
                                        randomGen.nextDouble()*6-3}));
                    }
                    mouseClicks.clear();
                }


                //DRAWING ALL THE NODES
                if (Nodes!=null && !Nodes.isEmpty()) {
                    GC.setFill(Color.BLACK);
                    GC.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);
                    GC.setFill(Color.WHITE);
                    for (Node n : Nodes) {

                        GC.fillOval(n.position[0], n.position[1], n.size*n.sizeModifier, n.size*n.sizeModifier);
                    }
                }




            }

        }.start();


    }


    private static void close() {
        System.out.println("Application closing");


        System.exit(0);
    }
}
