package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculateShortestPathTest {

    static private int [] distances;
    static Map<Integer, List<Integer>> shortestPaths = new HashMap<>();
    @BeforeAll
    static void setUpValues() {
        int source = 0;

        Node n0 = new Node(0,0);
        Node n1 = new Node (1, Integer.MAX_VALUE);
        Node n2 = new Node (2, Integer.MAX_VALUE);
        Node n3 = new Node (3, Integer.MAX_VALUE);
        Node n4 = new Node (4, Integer.MAX_VALUE);

        List <Node> nodes = new ArrayList<>();
        nodes.add(n0);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);

        Edge edge01 = new Edge(0, 1, 5);
        Edge edge02 = new Edge(0, 2, 3);
        Edge edge13 = new Edge(1, 3, 6);
        Edge edge23 = new Edge(2, 3, 2);
        Edge edge34 = new Edge(3, 4, 4);
        Edge edge04 = new Edge (0, 4, 1);

        List <Edge> edges = new ArrayList<>();
        edges.add(edge01);
        edges.add(edge02);
        edges.add(edge13);
        edges.add(edge23);
        edges.add(edge34);
        edges.add(edge04);

        Graph graph = new Graph(nodes, edges);
        CalculateShortestPath c = new CalculateShortestPath(graph);
        shortestPaths = c.calculatePath(source);
        distances = c.getDistance();
    }
    @Test
    void testShortestPaths() {
        assertNotNull(shortestPaths);
    }
    @Test
    void testDistancesFilled () {
        assertNotNull(distances);
    }
    @Test
    void checkIfDistancesAreCorrect(){
        assertEquals(5, distances[1]);
        assertEquals(1, distances[4]);
    }

}

