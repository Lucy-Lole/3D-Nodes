package com.lucylole.dijkstranodes;

import javafx.scene.paint.Color;
import java.util.Arrays;

public class Node {

    public double[] position;
    // [x,y,z]

    public double size,sizeModifier;
    public double[] centre = {0,0,0};
    private double[] speed;
    // [xSpeed,ySpeed,zSpeed]

    public Color nodeColor;


    Node(double[] position, double size, double sizeModifier, double[] speed, Color nodeColor) {

        this.nodeColor = nodeColor;
        this.position = position;
        this.size = size;
        this.sizeModifier = sizeModifier;
        this.speed = Arrays.copyOf(speed,3);
    }

    public void UpdatePosition(double[] boundaries,int coordAmount) {
        for (int i=0;i<=coordAmount;i++) {
            //update the position first
            position[i] += speed[i];
            centre[i] = (position[i]+size*sizeModifier);
            double diam = (size*sizeModifier);
            //now we check to see if our new position is out of bounds
            if ((position[i] + diam) > boundaries[i]) {
                position[i] = boundaries[i]-diam;
                speed[i] = -speed[i];
            } else if (position[i] < 0) {
                position[i] = 0;
                speed[i] = -speed[i];
            }

        }
    }

    public void UpdateSize(double[] boundaries) {
        double maxSizeModifier = 2.5;
        sizeModifier = (0.4+maxSizeModifier*(position[2]/boundaries[2]));
    }
}