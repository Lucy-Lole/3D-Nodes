import javafx.scene.canvas.*;

public class ScreenController {

    private long lastTime = 0;
    final long frameSpace = 1000000000/60;
    private boolean canUpdate;


    public boolean canUpdate(long now) {
        if (now >= lastTime + frameSpace) {
            canUpdate = true;

        }
        else { canUpdate = false;}
        lastTime = now;
        return canUpdate;
    }


}
