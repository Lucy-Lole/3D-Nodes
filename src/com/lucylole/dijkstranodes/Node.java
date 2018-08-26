package com.lucylole.dijkstranodes;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Node {

    public double[] position;
    // [x,y,z]

    public double size,sizeModifier;
    public double[] centre;
    private double[] speed;
    public ArrayList<Edge> edges;
    public boolean edgesChecked = false;
    public boolean checked = false;
    public double weight;
    public Node prevNode = null;
    // [xSpeed,ySpeed,zSpeed]


    public Color nodeColor;


    Node(double[] position, double size, double sizeModifier, double[] speed, Color nodeColor, double weight) {

        this.nodeColor = nodeColor;
        this.position = position;
        this.size = size;
        this.sizeModifier = sizeModifier;
        this.speed = Arrays.copyOf(speed,3);
        edges =  new ArrayList<>();
        this.weight = weight;
        this.centre = Arrays.copyOf(position,3);
    }

    public void UpdatePosition(double[] boundaries,int coordAmount) {
        for (int i=0;i<=coordAmount;i++) {
            //update the position first
            position[i] += speed[i];
            centre[i] = (position[i]+(size/2)*sizeModifier);
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


    public static double distance(Node n1,Node n2, boolean threeDTurnedOn) {
        return Math.sqrt(
                Math.pow(n1.position[0]-n2.position[0],2)
                + Math.pow(n1.position[1]-n2.position[1],2)
                + (threeDTurnedOn ? Math.pow(n1.position[2]-n2.position[2],2) : 0));
    }

    public void UpdateCentre() {
        for (int i=0;i<=2;i++) {
            centre[i] = (position[i] + (size / 2) * sizeModifier);
        }
    }

    public double GetWeight() {
        return weight;
    }


    public void UpdateSize(double[] boundaries) {
        double maxSizeModifier = 2.5;
        sizeModifier = (0.4+maxSizeModifier*(position[2]/boundaries[2]));
    }
}
