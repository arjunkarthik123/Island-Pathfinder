package ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;
public class Edge {
    int start;
    int end;
    int distanceTwoNodes;
    public Edge (int start, int end, int distanceTwoNodes){
        this.start = start;
        this.end = end;
        this.distanceTwoNodes = distanceTwoNodes;
    }
    public int getDistanceTwoNodes() {
        return distanceTwoNodes;
    }
    public int getStart() {
        return start;
    }
    public int getEnd() {
        return end;
    }
}
