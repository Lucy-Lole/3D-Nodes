import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.stage.*;

public class ScreenController extends Application {

    private Stage mainStage;

    private long targetTime = System.currentTimeMillis();

    private long frameSpace = 1000/60;

    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setTitle("3D-Nodes");
        mainStage.setResizable(false);
        mainStage.setMinWidth(400);
        mainStage.setMinHeight(400);
        mainStage.show();

        double mouseX = 0;
        double mouseY = 0;

        Canvas mainCanvas = new Canvas(mainStage.getMinWidth(),mainStage.getMinHeight());
        /*mainCanvas.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //mouseX = event.getX();
                //mouseY = event.getY();

            }
        });
        }*/
    }

    public void updateFrame(NodeList currentNodes) {
        //first we check if we've passed the target time
        if (System.currentTimeMillis() >= targetTime) {
            //now we find a new target time for the next frame
            targetTime = (System.currentTimeMillis() + frameSpace);
            //first we update the position of all the nodes since last frame
            for (Node i : currentNodes.Nodes) {
                i.updatePosition();
                /*Remove all the circles from the screen
                create each new circle, with the updated positions*/

            }
        }
    }
}
