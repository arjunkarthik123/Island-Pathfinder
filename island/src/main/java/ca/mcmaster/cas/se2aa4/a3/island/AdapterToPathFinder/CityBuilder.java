package ca.mcmaster.cas.se2aa4.a3.island.AdapterToPathFinder;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyPolygon;
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
    public CityBuilder (List <MyPolygon> polygons, Random rand, int cities){
        super(polygons, rand);
        this.cities = cities;
        executeAdapter();
        assignCityToNode();
    }
    private void assignCityToNode (){
        int nodeIndex;
        List<Integer> indexList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i<nodeList.size(); i++){
            determineCities.put(i, false);
        }

        for (int j = 0; j<cities; j++){
            nodeIndex = random.nextInt(nodeList.size());
            do {
                indexList.add(nodeIndex);
                determineCities.replace(nodeIndex, true);
                nodeIndex = random.nextInt(nodeList.size());
            } while (!indexList.contains(nodeIndex));
        }
    }
    private void determineCentroidsInCityNetwork () {
        Map<Integer, Node> cityNodes = new HashMap<>();
        List<Integer> trueNodesIndices = new ArrayList<>();;
        for (int i = 0; i<determineCities.size(); i++){
            if (determineCities.get(i)) {
                cityNodes.put(i, nodeList.get(i));
                trueNodesIndices.add(i);
            }
        }
        for (int j = 0; j<trueNodesIndices.size(); j++){
            nodeCentroids.add(centroids.get(j));
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
        a = (int) x/nodeCentroids.size();
        b = (int) y/nodeCentroids.size();

        Point mostCentralPoint = nodeCentroids.get(0);

        for (Point nodeCentroid: nodeCentroids) {
            int x_dist = (int) Math.abs(a - nodeCentroid.getX());
            int y_dist = (int) Math.abs(b - nodeCentroid.getY());
            int averageDistOne = (x_dist + y_dist)/2;

            int j_dist = (int) Math.abs(a - mostCentralPoint.getX());
            int k_dist = (int) Math.abs(b - mostCentralPoint.getY());
            int averageDistTwo = (j_dist + k_dist)/2;

            if (averageDistOne < averageDistTwo){
                mostCentralPoint = nodeCentroid;
            }
        }
        for (int i = 0; i<centroids.size(); i++){
            if (centroids.get(i) == mostCentralPoint){
                mostCentralIndex = i;
                break;
            }
        }
        mostCentralNode = nodeList.get(mostCentralIndex);
    }
}
