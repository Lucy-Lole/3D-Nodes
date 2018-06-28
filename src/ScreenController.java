import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;

public class ScreenController extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setTitle("3D-Nodes");
        mainStage.setResizable(false);
        mainStage.setMinWidth(400);
        mainStage.setMinHeight(400);
        mainStage.show();


    }

    public void initialiseScreen() {
        Stage mainStage =  new Stage();
        mainStage.show();

    }
}
