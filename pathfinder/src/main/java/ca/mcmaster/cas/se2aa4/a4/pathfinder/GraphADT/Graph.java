package ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;
import java.util.List;

public class Graph {
    List<Node>nodeList;
    List<Edge>edgeList;
    public Graph (List <Node> nodeList, List <Edge> edges) {
        this.edgeList = edges;
        this.nodeList = nodeList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
