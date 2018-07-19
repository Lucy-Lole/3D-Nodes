import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.stage.*;

public class ScreenController extends Application {

    private Stage mainStage;

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
        mainCanvas.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();

            }
        });
        mainCanvas.setOnMouseClicked(Nodes3D.createNode(mouseX,mouseY,nodes));

        }

    }

    public void updateFrame(Node[] nodes) {
        for (Node i: nodes) {
            i.updatePosition();
            Circle circ = new Circle(i.x,i.y,15);

        }


    }


}
