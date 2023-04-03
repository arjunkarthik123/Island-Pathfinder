package ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;

import java.util.Comparator;

public class Node implements Comparator<Node> {
    private int index;
    private String name;
    private int weight;

    public Node (int index, String name) {
        this.index = index;
        this.name = name;
    }

    public Node (int index, String name, int weight){
        this.index = index;
        this.name = name;
        this.weight = weight;
    }
    //All getters
    public int getIndex () {
        return this.index;
    }
    public String getName() {
        return this.name;
    }

    //All setters
    public void setIndex(int index){
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public int compare(Node n1, Node n2)
    {
        return Integer.compare(n1.weight, n2.weight);
    }
}
