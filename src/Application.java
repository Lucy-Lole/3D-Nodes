import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.*;
import java.util.*;
import javafx.animation.*;

public class Application {


    private final static double WIN_HEIGHT = 800;
    private final static double WIN_WIDTH = 1200;

    private final static double[] defaultPos = {400,600,200};
    private final static double defaultSize = 20;
    private final static double defaultSizeM = 2;

    private static HashSet<double[]> mouseClicks = new HashSet<>();
    private static ArrayList<Node> Nodes = new ArrayList<>();

    private static boolean threeDTurnedOn = true;
    private static boolean movementTurnedOn = true;

    private static Random randomGen = new Random();

    public static void main(String args[]) {
        JFXPanel panel = new JFXPanel();
        Platform.runLater(Application::start);
    }


    private static void start() {
        System.out.println("Application starting...");
        System.out.println("Initializing Screen Controller");


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



        mainScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    Application.close();
                    break;
                case SPACE:
                    threeDTurnedOn = !threeDTurnedOn;
                    System.out.println("3D off");
                    break;
                case B:
                    movementTurnedOn = !movementTurnedOn;
                    System.out.println("Movement off");
                    break;
            }

        });

        //Here we capture any mouse clicks, and store them as a double array in a hash set
        //the array has the structure: [x,y,click-type]
        canvas.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown()) {
                    mouseClicks.add(new double[] {event.getX(), event.getY(), 0});
                } else if (event.isSecondaryButtonDown()) {
                    mouseClicks.add(new double[] {event.getX(), event.getY(), 1});
                }
        });



        root.getChildren().add(canvas);
        GC.setFill(Color.BLACK);
        GC.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);

        //These root and end nodes will be used for our dijkstra algorithm
        Node rootNode = new Node(defaultPos,
                defaultSize,
                defaultSizeM,
                RandomSpeed(),
                Color.LIMEGREEN);

        Node endNode = new Node(new double[] {200,600,400},
                defaultSize,
                defaultSizeM,
                RandomSpeed(),
                Color.RED);

        Nodes.add(rootNode);
        Nodes.add(endNode);


        //This timer makes sure that the speed of the nodes is NOT affected by frame rate
        //as the animation is unreliable for a stable frame rate
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if ( Nodes!=null && !Nodes.isEmpty()){
                            for (Node n : Nodes) {
                                if (movementTurnedOn) n.UpdatePosition(boundaries,(1+ (threeDTurnedOn ? 1:0) ));
                                if (threeDTurnedOn) {
                                    n.UpdateSize(boundaries, threeDTurnedOn);
                                } else {
                                    n.sizeModifier =1;
                                }
                            }
                        }
                    }
                },0,1000/60
        );


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                GC.setStroke(Color.WHITE);
                GC.setFill(Color.WHITE);

                //CREATING NEW NODES IF MOUSE IS CLICKED
                if (mouseClicks!=null && !mouseClicks.isEmpty()) {
                    for (double[] click : mouseClicks) {
                        if (click[2] == 0) {
                            Nodes.add(new Node(click,
                                    defaultSize,
                                    defaultSizeM,
                                    RandomSpeed(),
                                    Color.WHITE));
                        } else if (click[2] == 1) {
                            Nodes.removeIf((Node n) -> (Math.hypot(n.centre[0]-click[0],n.position[1]-click[1]) < n.size*n.sizeModifier));
                        }
                    }
                    mouseClicks.clear();
                }

                //DRAWING ALL THE NODES
                GC.setFill(Color.BLACK);
                GC.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);
                if (Nodes!=null && !Nodes.isEmpty()) {

                    for (Node n : Nodes) {

                        GC.setFill(n.nodeColor);
                        GC.fillOval(n.position[0],n.position[1],
                                n.size*n.sizeModifier, n.size*n.sizeModifier);
                    }
                }
            }
        }.start();
    }


    private static double[] RandomSpeed() {

        return new double [] {
                randomGen.nextDouble()*6-3,
                randomGen.nextDouble()*6-3,
                randomGen.nextDouble()*3-1.5
        };
    }

    private static void close() {
        System.out.println("Application closing");
        System.exit(0);
    }
}
