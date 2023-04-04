package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;

import java.util.*;

public class CalculateShortestPath implements FindPath{

    private int distance[];
    private Set<Integer> settledNodes = new HashSet<>();
    private PriorityQueue<Node> priorityQueue;
    private int vertex;
    List<List<Node>> adjacentNodes;

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
                priorityQueue.add(new Node(n.getIndex(), "lol", distance[n.getIndex()]));
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
