package ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;

public class Edge {

    Node n1;
    Node n2;
    int distanceTwoNodes;
    public Edge (Node node1, Node node2, int distanceTwoNodes){
        this.n1 = node1;
        this.n2 = node2;
        this.distanceTwoNodes = distanceTwoNodes;
    }

    public int getDistanceTwoNodes() {
        return distanceTwoNodes;
    }
}
