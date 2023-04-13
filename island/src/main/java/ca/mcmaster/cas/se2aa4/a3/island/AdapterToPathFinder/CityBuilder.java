package ca.mcmaster.cas.se2aa4.a3.island.AdapterToPathFinder;

import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyPolygon;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MySegment;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyVertex;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm.CalculateShortestPath;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import org.locationtech.jts.geom.Point;

import java.util.*;
import java.util.List;
public class CityBuilder extends PolygonGraphAdapter {
    private final int cities;
    private final List<Point> nodeCentroids = new ArrayList<>();
    private final Map<Integer, Boolean> determineCities = new HashMap<>();
    private int mostCentralIndex;
    private final List<Integer> trueNodeIndices = new ArrayList<>();
    private final Map<Integer, List<Integer>> shortestPaths;
    private final List<Integer> nodesRelevantToShortestPath = new ArrayList<>();
    public CityBuilder(List<MyPolygon> polygons, Random rand, int cities, List<MyVertex> vertices, List<MySegment> segments) {
        super(polygons, rand, vertices, segments);
        this.cities = cities;
        executeAdapter();
        makeCities();
        colorCities();
        shortestPaths = generateShortestPaths();
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

        for (int i = 0; i < determineCities.size(); i++) {
            if (determineCities.get(i)) {
                Point toChange = centroids.get(i);
                for (MyVertex vertex : vertices) {
                    if (toChange.getX() == vertex.getX() && toChange.getY() == vertex.getY()) {
                        vertex.changeColor(colorCode);
                    }
                }
            }
        }
    }
    private void determineCentroidsInCityNetwork() {
        for (int i = 0; i < determineCities.size(); i++) {
            if (determineCities.get(i)) {
                trueNodeIndices.add(i);
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

        for (int j = 0; j < centroids.size(); j++) {
            if (trueNodeIndices.contains(j)) {
                List<Integer> path = shortestPaths.get(j);
                if (path.size() > 0) {
                    nodesRelevantToShortestPath.add(j);
                }
            }
        }
        return shortestPaths;
    }
    private void generateSegmentsFromVertices() {
        int indexV1 = 0;
        int indexV2 = 0;
        for (int index : nodesRelevantToShortestPath) {
            List<Integer> pathIndices = shortestPaths.get(index);
            for (int j = 0; j < (pathIndices.size() - 1); j++) {
                Point p1 = centroids.get(pathIndices.get(j));
                Point p2 = centroids.get(pathIndices.get(j + 1));

                for (int k = 0; k < vertices.size(); k++) {
                    if (p1.getX() == vertices.get(k).getX() && p1.getY() == vertices.get(k).getY()) {
                        indexV1 = k;
                    }
                    if (p2.getX() == vertices.get(k).getX() && p2.getY() == vertices.get(k).getY()) {
                        indexV2 = k;
                    }
                }
                MySegment segment = new MySegment(vertices.get(indexV1), vertices.get(indexV2));
                segments.add(segment);
            }
        }
    }
}