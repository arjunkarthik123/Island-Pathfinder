package ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;

import java.util.Comparator;
public class Node implements Comparator<Node> {
    private int index;
    private int weight;
    public Node () {}
    public Node (int index, int weight) {
        this.index = index;
        this.weight = weight;
    }
    public int getIndex () {
        return this.index;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    @Override public int compare(Node n1, Node n2)
    {
        return Integer.compare(n1.weight, n2.weight);
    }
}
