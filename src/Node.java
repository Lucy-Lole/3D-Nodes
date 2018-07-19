public class Node {
    public double x,y,z;

    private double size,sizeModifier;

    private double xSpeed,ySpeed,zSpeed;

    Node(double x, double y, double z, double size, double sizeModifier, double xSpeed,
         double ySpeed, double zSpeed) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.sizeModifier = sizeModifier;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public void updatePosition() {
        x += xSpeed;
        y += ySpeed;
        z += zSpeed;

    }

}
