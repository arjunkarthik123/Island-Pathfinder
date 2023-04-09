package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;

import java.util.*;

public class CalculateShortestPath implements FindPath{

    private final int[] distance;
    private final List<Integer> settledNodes;
    private final PriorityQueue<Node> priorityQueue;

    private final List<List<Integer>> shortestPaths;
    private final List <Integer> singlePath;
    private final int numNodes;
    private List<List<Node>> adjacentNodes;

    public CalculateShortestPath(Graph graph) {
        numNodes = graph.getNodeList().size();
        distance = new int[numNodes];
        settledNodes = new ArrayList<>();
        priorityQueue = new PriorityQueue<>(numNodes, new Node());
        shortestPaths = new ArrayList<>();
        singlePath = new ArrayList<>();
    }

    public int[] getDistance() {
        return distance;
    }

    @Override
    public void calculateNeighbours(int min) {
        int edgeDist;
        int newDist;

        for (int i = 0; i<adjacentNodes.get(min).size(); i++){
            Node n = adjacentNodes.get(min).get(i);

            if (!settledNodes.contains(n.getIndex())){
                edgeDist = n.getWeight();
                newDist = distance[min] + edgeDist;

                if (newDist < distance[n.getIndex()]) {
                    distance[n.getIndex()] = newDist;

                }

                priorityQueue.add(new Node(n.getIndex(), distance[n.getIndex()]));

            }
        }
    }
    @Override
    public List<List<Integer>> calculatePath(List <List<Node>> adjacentNodes, int source) {
        this.adjacentNodes = adjacentNodes;
        for (int i = 0; i< numNodes; i++){
            distance[i] = Integer.MAX_VALUE;
        }

        Node src = new Node(source, 0);
        priorityQueue.add(src);
        distance[source] = 0;

        while (settledNodes.size() != numNodes){
            singlePath.clear();
            singlePath.add(source);
            if (priorityQueue.isEmpty()){
                return shortestPaths;
            }
            int min = priorityQueue.remove().getIndex();
            singlePath.add(min);

            if (settledNodes.contains(min)){
                continue;
            }
            settledNodes.add(min);
            calculateNeighbours(min);
            shortestPaths.add(singlePath);
        }
        return shortestPaths;
    }
}
