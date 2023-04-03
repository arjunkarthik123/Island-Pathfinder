package ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;

public class Edge {

    Node n1;
    Node n2;
    int weight;
    public Edge (Node node1, Node node2, int weight){
        this.n1 = node1;
        this.n2 = node2;
        this.weight = weight;
    }
}
