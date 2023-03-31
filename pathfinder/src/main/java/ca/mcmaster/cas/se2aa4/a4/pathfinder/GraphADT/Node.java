package ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;

import java.awt.*;

public class Node {
    private int elevation;
    private int index;
    private String name;

    private Point centroid;

    public Node (int elevation, int index, String name, Point vertex){
        this.elevation = elevation;
        this.index = index;
        this.name = name;
        this.centroid = vertex;
    }
    //All getters
    public int getIndex () {
        return this.index;
    }
    public int getElevation () {
        return this.elevation;
    }

    public String getName() {
        return this.name;
    }

    public Point getCentroid() {
        return centroid;
    }

    //All setters
    public void setIndex(int index){
        this.index = index;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }
}
