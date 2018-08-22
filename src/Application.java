import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.animation.*;


public class Application {


    private final static double WIN_HEIGHT = 800;
    private final static double WIN_WIDTH = 1200;

    private final static double[] defaultPos = {400,600,200};
    private final static double defaultSize = 10;
    private final static double defaultSizeM = 1;

    private static HashSet<double[]> mouseClicks = new HashSet<>();
    private static ArrayList<Node> Nodes = new ArrayList<>();

    private static Random randomGen = new Random();

    public static void main(String args[]) {
        JFXPanel panel = new JFXPanel();
        Platform.runLater(Application::start);
    }


    private static void start() {
        System.out.println("Application starting...");
        System.out.println("Initializing Screen Controller");

        //ScreenController controller = new ScreenController();

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

        //These root and end nodes will be used for our dijkstra algorithm
        Node rootNode = new Node(defaultPos,defaultSize,defaultSizeM,RandomSpeed(),Color.GREEN);
        Node endNode = new Node(new double[] {200,600,400},defaultSize,defaultSizeM,RandomSpeed(),Color.RED);
        Nodes.add(rootNode);
        Nodes.add(endNode);


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                GC.setStroke(Color.WHITE);
                GC.setFill(Color.WHITE);

                //UPDATING NODES POSITIONS
                if ( Nodes!=null && !Nodes.isEmpty()){
                    for (Node n : Nodes) {
                        n.UpdatePosition(boundaries);
                    }
                }

                //CREATING NEW NODES IF MOUSE IS CLICKED
                if (mouseClicks!=null && !mouseClicks.isEmpty()) {

                    for (double[] pos : mouseClicks) {
                        Nodes.add(new Node(pos, defaultSize, defaultSizeM,
                                new double[] {
                                        randomGen.nextDouble()*6-3,
                                        randomGen.nextDouble()*6-3,
                                        randomGen.nextDouble()*6-3}, Color.WHITE));
                    }
                    mouseClicks.clear();
                }

                //DRAWING ALL THE NODES
                if (Nodes!=null && !Nodes.isEmpty()) {
                    GC.setFill(Color.BLACK);
                    GC.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);
                    for (Node n : Nodes) {

                        GC.setFill(n.nodeColor);
                        GC.fillOval(n.position[0], n.position[1], n.size*n.sizeModifier, n.size*n.sizeModifier);
                    }
                }
            }
        }.start();
    }

    private static double[] RandomSpeed() {

        return new double [] {
                randomGen.nextDouble()*6-3,
                randomGen.nextDouble()*6-3,
                randomGen.nextDouble()*6-3
        };
    }

    private static void close() {
        System.out.println("Application closing");
        System.exit(0);
    }
}
