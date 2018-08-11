import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

public class ScreenController {

    private Stage mainStage;

    private long targetTime = System.currentTimeMillis();
    private long frameSpace = 1000/60;

    public ScreenController() {
        mainStage = new Stage();
    }

    private Canvas mainCanvas;
    private GraphicsContext GC;
    private double mouseX,mouseY;


    public void initialize() {
        mainStage.setTitle("3D-Nodes");
        mainStage.setResizable(false);
        mainStage.setMinWidth(400);
        mainStage.setMinHeight(400);
        mainStage.show();
        mainCanvas = new Canvas(mainStage.getMinWidth(),mainStage.getMinHeight());
        GC = mainCanvas.getGraphicsContext2D();

    }




    public void updateFrame(NodeList currentNodes) throws Exception {
        System.out.println("Starting frame update");
        System.out.println(targetTime);
        System.out.println(System.currentTimeMillis());
        //first we check if we've passed the target time
        if (System.currentTimeMillis() > targetTime) {
            //now we find a new target time for the next frame
            targetTime = (System.currentTimeMillis() + frameSpace);
            //then we update the position of all the nodes since last frame
            System.out.println("Updating positions!");
            currentNodes.updatePositions();
            System.out.println("Drawing nodes");
            for (Node i : currentNodes.Nodes) {
                /*Remove all the circles from the screen
                create each new circle, with the updated positions*/
                GC.clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
                GC.strokeOval(i.position[0],i.position[1],10,10);

            }
        }
        mainCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();
                double[] position = {mouseX,mouseY,0};
                double[] speed = {10,10,10};
                currentNodes.AddNode(position,1,1,speed);
                System.out.println("Mouse Click!");
            }
        });


    }
}
