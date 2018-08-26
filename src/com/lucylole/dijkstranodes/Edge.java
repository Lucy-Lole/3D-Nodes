package com.lucylole.dijkstranodes;

import javafx.scene.paint.Color;

public class Edge {
    public Node origin;
    public Node end;
    public double distance;
    public Color color;

    Edge(Node origin, Node end, double distance, Color color) {
        this.origin = origin;
        this.end = end;
        this.distance = distance;
        this.color = color;
    }

    public double GetDistance() {
        return distance;
    }
}
