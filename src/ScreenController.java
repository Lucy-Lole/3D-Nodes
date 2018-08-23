import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class ScreenController {

    ScreenController() {
        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        System.out.println("322");
                    }
                },0,100
        );
    }


    public boolean CanUpdate(long now) {
        final long frameSpace = 1000000000/60;
        long lastTime;
        boolean canUpdate;
        lastTime = now;
        return (now >= lastTime + frameSpace);
    }


}
