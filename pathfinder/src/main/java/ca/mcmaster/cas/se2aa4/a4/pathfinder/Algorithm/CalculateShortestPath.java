package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;

import java.util.*;

public class CalculateShortestPath implements FindPath {
    private final int[] distance;
    private final PriorityQueue<Node> priorityQueue;
    private final Map<Integer, List<Integer>> shortestPaths;
    private final int numNodes;

    private final Graph graph;
    public CalculateShortestPath(Graph graph) {
        this.graph = graph;
        numNodes = graph.getNodeList().size();
        distance = new int[numNodes];
        priorityQueue = new PriorityQueue<>(numNodes, new Node());
        shortestPaths = new HashMap<>();
    }
    public int[] getDistance() {
        return distance;
    }

    @Override
    public Map<Integer, List<Integer>> calculatePath(int source) {
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        Node src = new Node(source, 0);
        priorityQueue.offer(src);

        boolean [] visited = new boolean[numNodes];
        int [] senior = new int[numNodes];
        Arrays.fill(senior, -1);

        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i<numNodes; i++){
            paths.add(new ArrayList<>());
        }

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            int currentIndex = current.getIndex();

            if (visited[currentIndex]) {
                continue;
            }
            visited[currentIndex] = true;

            for (Edge edge : graph.getEdgeList()){
                int start = edge.getStart();
                int end = edge.getEnd();
                int distanceToOther = edge.getDistanceTwoNodes();

                if (start == currentIndex && distanceToOther > 0){
                    int newDistance = distance[currentIndex] + distanceToOther;
                    if (newDistance < distance[end]){
                        distance[end] = newDistance;
                        senior[end] = currentIndex;
                        Node n = new Node(end, newDistance);
                        priorityQueue.offer(n);
                    }
                }
            }
        }
        for (int k = 0; k<numNodes; k++){
            List <Integer> path = new ArrayList<>();
            for (int l = k; l != -1; l = senior[l]){
                path.add(l);
            }
            Collections.reverse(path);
            paths.set(k, path);
        }

        for (int m = 0; m<numNodes; m++){
            if (m != source) {
                shortestPaths.put(m, paths.get(m));
            }
        }
        shortestPaths.put(source, Collections.emptyList());
        return shortestPaths;
    }
}