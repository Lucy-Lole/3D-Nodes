public class ScreenController {

    public boolean CanUpdate(long now) {
        final long frameSpace = 1000000000/60;
        long lastTime;
        boolean canUpdate;
        lastTime = now;
        return (now >= lastTime + frameSpace);
    }


}
