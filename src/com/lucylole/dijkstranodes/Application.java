package com.lucylole.dijkstranodes;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.*;
import java.util.*;
import javafx.animation.*;

public class Application {
    //Window vars
    private final static double WIN_HEIGHT = 800;
    private final static double WIN_WIDTH = 1200;
    private final static int framesPerSecond = 60;
    private static HashSet<double[]> mouseClicks = new HashSet<>();

    //Node vars
    private final static double[] defaultPos = {400,600,200};
    private final static double defaultSize = 20;
    private final static double defaultSizeM = 2;
    private static double speedModifier = 1;
    private static ArrayList<Node> Nodes = new ArrayList<>();

    //Text vars
    private static ArrayList<Text> textToDisplay = new ArrayList<>();
    private static boolean threeDTurnedOn = true;
    private static boolean movementTurnedOn = true;
    private static boolean dijkstraTurnedOn = true;
    private static int setFramesElapsed;
    private static int framesElapsed;

    //Dijkstra vars
    private static double tetherDistance = 200;
    private static ArrayList<Edge> edgesToDraw = new ArrayList<>();

    //Other vars
    private static Random randomGen = new Random();

    public static void main(String args[]) {
        new JFXPanel();
        Platform.runLater(Application::start);
    }

    private static void start() {
        System.out.println("Application starting...");

        Group root = new Group();
        Stage mainStage =  new Stage();
        Scene mainScene = new Scene(root);
        Canvas canvas = new Canvas();

        mainStage.setResizable(false);
        try {
            mainStage.getIcons().add(new Image(
                    Application.class.getResource("favicon.png").toExternalForm()));
        } catch (Exception e) {
            System.out.println("Failed to load icon: " + e.getMessage());
        }
        mainStage.setHeight(WIN_HEIGHT);
        mainStage.setWidth(WIN_WIDTH);
        mainStage.setTitle("Dijkstra Nodes");
        mainStage.setScene(mainScene);
        canvas.setWidth(mainStage.getWidth());
        canvas.setHeight(mainStage.getHeight());
        GraphicsContext GC = canvas.getGraphicsContext2D();

        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(9);
        textFlow.setLayoutY(647);

        Text threeDText = new Text("\n3-D Mode (Space)");
        Text moveText = new Text("\nNode Movement (B)");
        Text dijkstraText = new Text("\nDijkstra's Visible (V)");
        Text clearText = new Text("\nPress (C) to clear nodes");
        Text tetherText = new Text();
        Text nodeCount = new Text();
        Text speedText = new Text();
        Text fpsText =  new Text();

        clearText.setFill(Color.YELLOW);
        tetherText.setFill(Color.YELLOW);
        nodeCount.setFill(Color.YELLOW);
        speedText.setFill(Color.YELLOW);
        fpsText.setFill(Color.YELLOW);


        Collections.addAll(textToDisplay,
                fpsText,
                threeDText,
                moveText,
                dijkstraText,
                nodeCount,
                clearText,
                tetherText,
                speedText);

        textToDisplay.forEach((Text t) -> t.setFont(Font.font("Consolas",12)));
        textFlow.getChildren().addAll(textToDisplay);

        //There seems to be some weirdness where the canvas and stage can't agree on the same
        //size, so i've manually found the size difference and fixed it
        double [] boundaries = {canvas.getWidth()-17,canvas.getHeight()-40,400};

        root.getChildren().add(canvas);
        root.getChildren().add(textFlow);
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
                case C:
                    Nodes.removeIf((Node n) -> (n.nodeColor == Color.WHITE));
                    break;
                case V:
                    dijkstraTurnedOn = !dijkstraTurnedOn;
                    break;
                case UP:
                    tetherDistance += 10;
                    break;
                case DOWN:
                    tetherDistance -= 10;
                    break;
                case LEFT:
                    speedModifier -= 0.1;
                    break;
                case RIGHT:
                    speedModifier += 0.1;
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

        canvas.setOnMouseDragged(event -> {
            if (event.isSecondaryButtonDown()) {
                mouseClicks.add(new double[]{event.getX(), event.getY(), 1});
            } else if (event.isPrimaryButtonDown()) {
                mouseClicks.add(new double[] {event.getX(), event.getY(), 0});
            }


        });

        //These root and end nodes will be used for our dijkstra algorithm
        Node rootNode = new Node(defaultPos,
                defaultSize,
                defaultSizeM,
                RandomSpeed(),
                Color.LIMEGREEN,
                0);
        Node endNode = new Node(new double[] {200,600,400},
                defaultSize,
                defaultSizeM,
                RandomSpeed(),
                Color.RED,
                Double.MAX_VALUE);
        Nodes.add(rootNode);
        Nodes.add(endNode);

        //This timer makes sure that the speed of the nodes is NOT affected by frame rate
        //frame rate is unreliable for stable movement
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if ( Nodes!=null && !Nodes.isEmpty()){
                            try {
                                for (Node n : Nodes) {
                                    if (movementTurnedOn)
                                        n.UpdatePosition(
                                                boundaries,
                                                (1 + (threeDTurnedOn ? 1 : 0)),
                                                speedModifier);
                                    if (threeDTurnedOn) {
                                        n.UpdateSize(boundaries);
                                    } else {
                                        n.UpdateCentre();
                                        n.sizeModifier = 1;
                                    }
                                }

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        setFramesElapsed += 1;
                    }
                },0,1000/framesPerSecond
        );

        //
        //RUNNING EVERY FRAME:
        //

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                //CREATING NEW NODES IF MOUSE IS CLICKED
                if (mouseClicks!=null && !mouseClicks.isEmpty()) {
                    for (double[] click : mouseClicks) {
                        if (click[2] == 0) {
                            Node newNode = new Node(click,
                                    defaultSize,
                                    defaultSizeM,
                                    RandomSpeed(),
                                    Color.WHITE,
                                    Double.MAX_VALUE);
                            Nodes.add(newNode);
                            newNode.UpdateSize(boundaries);
                        } else if (click[2] == 1) {
                            Nodes.removeIf((Node n) ->
                                    (Math.hypot(n.centre[0]-click[0],
                                            n.position[1]-click[1]) < n.size*n.sizeModifier)
                                            && n.nodeColor == Color.WHITE);
                        }
                    }
                    mouseClicks.clear();
                }

                /*DIJKSTRA'S PROCESSING */

                //CLEARING THE PREVIOUS EDGES
                edgesToDraw.clear();
                Nodes.forEach((Node n ) -> {
                    n.edgesChecked = false;
                    n.edges.clear();
                    n.nodeColor = Color.WHITE;

                });

                //FINDING ALL THE EDGES
                for (Node n : Nodes) {
                    for (Node potentialNode : Nodes) {
                        if (!potentialNode.edgesChecked) {
                            double distanceBetween = Node.distance(
                                    n,
                                    potentialNode,
                                    threeDTurnedOn);
                            if (distanceBetween <= tetherDistance) {
                                //This adds it to our drawing list
                                edgesToDraw.add(new Edge(n,
                                        potentialNode,
                                        distanceBetween,
                                        Color.WHITE));
                                //This adds it to the first nodes connections list
                                n.edges.add(new Edge(n,
                                        potentialNode,
                                        distanceBetween,
                                        Color.WHITE));
                                //this adds it to the second nodes connections list
                                potentialNode.edges.add(new Edge(potentialNode,
                                        n,
                                        distanceBetween,
                                        Color.WHITE));
                            }
                        }
                    }
                    n.edgesChecked = true;
                }

                //PERFORMING THE PATH FINDING
                Nodes.forEach((Node n) -> {
                    n.weight = Double.MAX_VALUE;
                    n.checked = false;
                    n.prevNode = null;
                });
                rootNode.weight = 0;

                Nodes.sort(Comparator.comparingDouble(Node::GetWeight));
                Node n = Nodes.get(0);
                do {
                    n.edges.sort(Comparator.comparingDouble(Edge::GetDistance));
                    for (Edge e : n.edges) {
                        double tempWeight = n.weight + e.distance;
                        if (tempWeight < e.end.weight) {
                            e.end.weight = tempWeight;
                            e.end.prevNode = n;
                        }
                    }
                    n.checked = true;
                    n = null;
                    Nodes.sort(Comparator.comparingDouble(Node::GetWeight));
                    double lowestWeight = Double.MAX_VALUE;
                    for (Node nextNode : Nodes) {
                        if (!nextNode.checked && nextNode.weight < lowestWeight) {
                            n = nextNode;
                            lowestWeight = nextNode.weight;
                        }
                    }
                } while (n != null);

                //DRAWING THE EDGES
                GC.setFill(Color.BLACK);
                GC.fillRect(0,0,WIN_WIDTH,WIN_HEIGHT);
                if (edgesToDraw!=null && !edgesToDraw.isEmpty()) {
                    GC.setLineWidth(1);
                    for (Edge e : edgesToDraw) {
                        GC.setStroke(e.color);
                        GC.strokeLine(
                                e.origin.centre[0],
                                e.origin.centre[1],
                                e.end.centre[0],
                                e.end.centre[1]);
                    }
                }

                //DRAWING THE PATH
                n = endNode;
                GC.setStroke(Color.YELLOW);
                GC.setLineWidth(4);
                while (n.prevNode != null && dijkstraTurnedOn) {
                    n.nodeColor = Color.YELLOW;
                    GC.strokeLine(
                            n.centre[0],
                            n.centre[1],
                            n.prevNode.centre[0],
                            n.prevNode.centre[1]);
                    n = n.prevNode;
                }

                //DRAWING ALL THE NODES
                Nodes.forEach((Node node) -> {
                    if (node == endNode) {
                        node.nodeColor = Color.RED;
                    } else if (node == rootNode) {
                        node.nodeColor = Color.LIMEGREEN;
                    }
                });
                if (Nodes!=null && !Nodes.isEmpty()) {
                    for (Node drawNode : Nodes) {
                        GC.setFill(drawNode.nodeColor);
                        GC.fillOval(drawNode.position[0],
                                drawNode.position[1],
                                drawNode.size*drawNode.sizeModifier,
                                drawNode.size*drawNode.sizeModifier);
                    }
                }

                //DRAWING THE LABELS
                framesElapsed += 1;
                if (setFramesElapsed >= framesPerSecond) {
                    fpsText.setText("FPS: " + framesElapsed);
                    setFramesElapsed = 0;
                    framesElapsed = 0;
                }


                threeDText.setFill(threeDTurnedOn ? Color.LIMEGREEN : Color.RED);
                dijkstraText.setFill(dijkstraTurnedOn ? Color.LIMEGREEN : Color.RED);
                moveText.setFill(movementTurnedOn ? Color.LIMEGREEN : Color.RED);
                nodeCount.setText("\nNode count: " + Nodes.size());
                speedText.setText(
                        "\nPress (LEFT) or (RIGHT) to change speed modifier. Current Modifier:  "
                                + String.format("%.1f", speedModifier));
                tetherText.setText(
                        "\nPress (UP) or (DOWN) to change tether distance. Tether Distance: "
                                + ((int) tetherDistance));
            }
        }.start();
    }

    private static double[] RandomSpeed() {
        return new double [] {
                randomGen.nextDouble()*3-1.5,
                randomGen.nextDouble()*3-1.5,
                randomGen.nextDouble()*3-1.5
        };
    }

    private static void close() {
        System.out.println("Application closing");
        System.exit(0);

    }
}
