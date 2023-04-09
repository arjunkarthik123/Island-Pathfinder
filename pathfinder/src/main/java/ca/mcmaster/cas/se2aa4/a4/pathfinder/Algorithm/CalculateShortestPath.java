package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;

import java.util.*;

public class CalculateShortestPath implements FindPath{

    private final int[] distance;
    private final Set<Integer> settledNodes = new HashSet<>();
    private final PriorityQueue<Node> priorityQueue;
    private final int vertex;

    Graph graph;
    List<List<Node>> adjacentNodes;

    public CalculateShortestPath(int[] distance, PriorityQueue<Node> priorityQueue, int vertex, Graph graph) {
        this.distance = distance;
        this.priorityQueue = priorityQueue;
        this.vertex = vertex;
        this.graph = graph;
    }

    @Override
    public void calculateNeighbours(int test) {
        int edgeDist;
        int newDist;

        for (int i = 0; i<adjacentNodes.get(test).size(); i++){
            Node n = adjacentNodes.get(test).get(i);

            if (!settledNodes.contains(n.getIndex())){
                edgeDist = n.getWeight();
                newDist = distance[test] + edgeDist;

                if (newDist < distance[n.getIndex()]) {
                    distance[n.getIndex()] = newDist;
                }
                priorityQueue.add(new Node(n.getIndex(), distance[n.getIndex()]));
            }
        }
    }
    @Override
    public void calculatePath(List<List<Node> > adjacentNodes, int source, String sourceName) {
        this.adjacentNodes = adjacentNodes;
        for (int i = 0; i<vertex; i++){
            distance[i] = Integer.MAX_VALUE;
        }

        Node src = new Node(source, sourceName, 0);
        priorityQueue.add(src);
        distance[source] = 0;

        while (settledNodes.size() != vertex){
            if (priorityQueue.isEmpty()){
                return;
            }
            int min = priorityQueue.remove().getIndex();
            if (settledNodes.contains(min)){
                continue;
            }
            settledNodes.add(min);
            calculateNeighbours(min);
        }
    }
}
