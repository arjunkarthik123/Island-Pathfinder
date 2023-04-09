package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CalculateShortestPathTest {

    @Test
    void calculateNeighbours() {
    }

    @Test
    void testShortestPath() {
        int numNodes = 5;
        int source = 0;
        List<List<Node>> adjacentNodes = new ArrayList<>();
        for (int i = 0; i<numNodes; i++){
            List<Node> x = new ArrayList<>();
            adjacentNodes.add(x);
        }

        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        Node n1 = new Node(1,1);
        nodes.add(n1);
        Node n2 = new Node(2,7);
        nodes.add(n2);
        Node n3 = new Node(3,12);
        nodes.add(n3);
        Node n4 = new Node(4,33);
        nodes.add(n4);
        Node n5 = new Node(1,21);
        nodes.add(n5);
        Node n6 = new Node(3,8);
        nodes.add(n6);


        adjacentNodes.get(0).add(n1);
        adjacentNodes.get(0).add(n2);
        adjacentNodes.get(0).add(n3);
        adjacentNodes.get(0).add(n4);

        adjacentNodes.get(2).add(n5);
        adjacentNodes.get(2).add(n6);



        Graph g = new Graph(nodes, edges);
        CalculateShortestPath c = new CalculateShortestPath(g);
        List<List<Integer>> shortestPath;
        shortestPath = c.calculatePath(adjacentNodes, source);

        System.out.println("The shorted path from node 0:");

        for (int i = 0; i < c.getDistance().length-1; i++)
            System.out.println(source + " to " + i + " is "+ c.getDistance()[i]);

        System.out.println("Now printing all nodes in each of the shortest paths from 0");
        for (int i = 0; i<shortestPath.size(); i++){
            for (int j = 0; j<shortestPath.get(i).size();j++){
                System.out.print(shortestPath.get(i).get(j)+", ");
            }
            System.out.println(" ");
        }
        System.out.println("size of shortest path: "+shortestPath.size());
        System.out.println("number of nodes: "+nodes.size());
    }
}

