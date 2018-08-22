
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Node {

    public double[] position;
    // [x,y,z]

    public double size,sizeModifier;

    private double diam = 2*size*sizeModifier;

    private double[] centre = {0,0,0};

    private double[] speed;

    public Color nodeColor;


    // [xSpeed,ySpeed,zSpeed]

    Node(double[] position, double size, double sizeModifier, double[] speed, Color nodeColor) {

        this.nodeColor = nodeColor;
        this.position = position;
        this.size = size;
        this.sizeModifier = sizeModifier;
        this.speed = Arrays.copyOf(speed,3);
    }

    public void UpdatePosition(double[] boundaries) {
        for (int i=0;i<=2;i++) {
            //update the position first
            position[i] += speed[i];
            //now we check to see if our new position is out of bounds
            if (position[i] > boundaries[i]) {
                //if were out the far boundary we get it back inside and invert the movement
                position[i] = boundaries[i]-diam;
                speed[i] = -speed[i];
            } else if ((position[i] + diam) > boundaries[i]) {
                position[i] = boundaries[i]-diam;
                speed[i] = -speed[i];
            } else if (position[i] < 0) {
                position[i] = 0;
                speed[i] = -speed[i];
            }
            centre[i] = (position[i]+size);
        }



    }

}
