package ca.mcmaster.cas.se2aa4.a3.island.AdapterToPathFinder;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyPolygon;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MySegment;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyVertex;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm.CalculateShortestPath;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;
import org.locationtech.jts.geom.Point;

import java.util.*;
import java.util.List;

public class CityBuilder extends PolygonGraphAdapter {
    private final int cities;
    private final List<Point> nodeCentroids = new ArrayList<>();
    private final Map<Integer, Boolean> determineCities = new HashMap<>();
    private int mostCentralIndex;
    public Node mostCentralNode;
    private final List<Integer> trueNodeIndices = new ArrayList<>();
    private Map<Integer, List<Integer>> shortestPaths;
    private final List<Node> cityNodeList = new ArrayList<>();
    Map<Integer, MyVertex> newVertices = new HashMap<>();

    private final List<Integer> nodesRelevantToShortestPath = new ArrayList<>();

    public CityBuilder(List<MyPolygon> polygons, Random rand, int cities, List<MyVertex> vertices, List<MySegment> segments) {
        super(polygons, rand, vertices, segments);
        this.cities = cities;
        executeAdapter();
        makeCities();
        colorCities();
        shortestPaths = generateShortestPaths();
        //changeCapitolCityColor();
        generateNewVerticesFromNodeCentroids();
        generateSegmentsFromVertices();
    }

    private void assignCityToNode() {
        int nodeIndex;
        List<Integer> indexList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < nodeList.size(); i++) {
            determineCities.put(i, false);
        }

        int k = 0;
        while (k < cities) {
            nodeIndex = random.nextInt(nodeList.size());
            if (!indexList.contains(nodeIndex)) {
                indexList.add(nodeIndex);
                determineCities.replace(nodeIndex, true);
                k++;
            }
        }
    }

    private void colorCities() {
        String colorCode = "255,255,0";
        System.out.println(centroids.size());
        System.out.println(determineCities.size());

        for (int i = 0; i < determineCities.size(); i++) {
            if (determineCities.get(i)) {
                Point toChange = centroids.get(i);
                for (int j = 0; j < vertices.size(); j++) {
                    if (toChange.getX() == vertices.get(j).getX() && toChange.getY() == vertices.get(j).getY()) {
                        vertices.get(j).changeColor(colorCode);
                    }
                }
            }
        }
    }

    private void determineCentroidsInCityNetwork() {
        Map<Integer, Node> cityNodes = new HashMap<>();
        for (int i = 0; i < determineCities.size(); i++) {
            if (determineCities.get(i)) {
                cityNodes.put(i, nodeList.get(i));
                trueNodeIndices.add(i);
                cityNodeList.add(nodeList.get(i));
            }
        }
        for (Integer trueNodeIndex : trueNodeIndices) {
            nodeCentroids.add(centroids.get(trueNodeIndex));
        }
    }

    private void determineMostCentralNode() {
        double x = 0;
        double y = 0;
        int a;
        int b;

        for (Point nodeCentroid : nodeCentroids) {
            x += nodeCentroid.getX();
            y += nodeCentroid.getY();
        }
        a = (int) x / nodeCentroids.size();
        b = (int) y / nodeCentroids.size();

        Point mostCentralPoint = nodeCentroids.get(0);

        for (Point nodeCentroid : nodeCentroids) {
            int x_dist = (int) Math.abs(a - nodeCentroid.getX());
            int y_dist = (int) Math.abs(b - nodeCentroid.getY());
            int averageDistOne = (x_dist + y_dist) / 2;

            int j_dist = (int) Math.abs(a - mostCentralPoint.getX());
            int k_dist = (int) Math.abs(b - mostCentralPoint.getY());
            int averageDistTwo = (j_dist + k_dist) / 2;

            if (averageDistOne < averageDistTwo) {
                mostCentralPoint = nodeCentroid;
            }
        }
        for (int i = 0; i < centroids.size(); i++) {
            if (centroids.get(i) == mostCentralPoint) {
                mostCentralIndex = i;
                break;
            }
        }
        nodeList.get(mostCentralIndex).setWeight(0);
        mostCentralNode = nodeList.get(mostCentralIndex);

        String colorCode = "255,0,0";
        Point toChange = centroids.get(mostCentralIndex);
        for (int j = 0; j < vertices.size(); j++) {
            if (toChange.getX() == vertices.get(j).getX() && toChange.getY() == vertices.get(j).getY()) {
                vertices.get(j).changeColor(colorCode);
                break;
            }
        }

    }

    @Override
    public void generateEdges() {
        for (int i = 0; i < polygons.size(); i++) {
            for (int j = 0; j < polygons.size(); j++) {
                if (j != i) {
                    if (polygons.get(i).isNeighbour(polygons.get(j))) {
                        edgeList.add(new Edge(i, j, calcDistance(centroids.get(i), centroids.get(j))));
                    }
                }
            }
        }
    }

    @Override
    public void generateGraph() {
        graph = new Graph(nodeList, edgeList);
    }

    private void makeCities() {
        assignCityToNode();
        determineCentroidsInCityNetwork();
        determineMostCentralNode();
        generateEdges();
        generateGraph();
    }

    private Map<Integer, List<Integer>> generateShortestPaths() {
        Map<Integer, List<Integer>> shortestPaths;
        CalculateShortestPath calc = new CalculateShortestPath(graph);
        shortestPaths = calc.calculatePath(mostCentralIndex);

        int[] distances = calc.getDistance();
        System.out.println("Source node is: " + mostCentralIndex);

        for (int j = 0; j < centroids.size(); j++) {
            if (trueNodeIndices.contains(j)) {
                List<Integer> path = shortestPaths.get(j);
                if (path.isEmpty()) {
                    System.out.print("source node, no path,");
                }
                if (path.size() > 0) {
                    System.out.print("Shortest path from source node to node " + j + ": ");
                    nodesRelevantToShortestPath.add(j);
                    System.out.print(path.get(0));
                    for (int k = 1; k < path.size(); k++) {
                        System.out.print(" -> " + path.get(k));
                    }
                }
                System.out.println(" distance " + distances[j]);
            }
        }
        return shortestPaths;
    }

    private void generateNewVerticesFromNodeCentroids() {
        int count = 0;
        for (int i = 0; i < centroids.size(); i++) {
            if (trueNodeIndices.contains(i)) {
                Point p = centroids.get(i);
                for (int j = 0; j < vertices.size(); j++) {
                    if (p.getX() == vertices.get(j).getX() && p.getY() == vertices.get(j).getY()) {
                        MyVertex v = vertices.get(j);
                        newVertices.put(i, v);
                        System.out.println(i + " associated vertex index: " + v.getIndex());
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }

    private void generateSegmentsFromVertices() {
        int indexV1 = 0;
        int indexV2 = 0;
        for (int i = 0; i < nodesRelevantToShortestPath.size(); i++) {
            int index = nodesRelevantToShortestPath.get(i);
            List<Integer> pathIndices = shortestPaths.get(index);
            for (int j = 0; j<(pathIndices.size() - 1); j++) {
                Point p1 = centroids.get(pathIndices.get(j));
                Point p2 = centroids.get(pathIndices.get(j+1));

                for (int k = 0; k<vertices.size(); k++){
                    if (p1.getX() == vertices.get(k).getX() && p1.getY() == vertices.get(k).getY()){
                        indexV1 = k;
                    }
                    if (p2.getX() == vertices.get(k).getX() && p2.getY() == vertices.get(k).getY()){
                        indexV2 = k;
                    }
                }
                MySegment segment = new MySegment(vertices.get(indexV1), vertices.get(indexV2));
                segments.add(segment);
            }


        }

    }
}

