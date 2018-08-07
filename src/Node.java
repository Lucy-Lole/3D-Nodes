public class Node {

    public double[] position;
    // [x,y,z]

    private double size,sizeModifier;

    private double[] speed;
    // [xSpeed,ySpeed,zSpeed]

    Node(double[] position, double size, double sizeModifier, double[] speed) {

        this.position = position;
        this.size = size;
        this.sizeModifier = sizeModifier;
        this.speed = speed;
    }

    public void updatePosition() {
        for (int i=0;i<=2;i++) {
            position[i] += speed[i];
        }

    }

}
