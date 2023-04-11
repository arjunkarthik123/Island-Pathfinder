package ca.mcmaster.cas.se2aa4.a3.island.AdapterToPathFinder;


import ca.mcmaster.cas.se2aa4.a3.island.Builders.IslandBuilder;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyPolygon;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyVertex;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;
import org.locationtech.jts.geom.Point;
import java.util.ArrayList;
import java.util.List;


import java.awt.*;
import java.util.Map;

public class PolygonGraphAdapter {
    private MyPolygon polygon;
    private Graph graph;
    private Map<MyVertex, Integer> nodeMapping;
    private List<MyPolygon> polygons;
    private List<Point> centroids;
    private List <Node> nodeList;
    private List<Edge> edgeList;

    public PolygonGraphAdapter (IslandBuilder i) {
        polygons = i.findPolygonsWithinIsland();
    }
    private int calcDistance(Point p1, Point p2){
        double d1 = Math.pow((p1.getX() - p2.getX()), 2);
        double d2 = Math.pow(p1.getY() - p2.getY(), 2);
        int distance = (int) Math.pow((d1 + d2), 0.5);
        return distance;
    }
    private void getAllCentroids(){
        for (MyPolygon myPolygon : polygons) {
            centroids.add(myPolygon.getCenterOfPolygon());
        }
    }
    private void mapNodeCentroid(){
        for (int i = 0; i<centroids.size(); i++){
            nodeList.add(new Node(i));
        }
    }

    private void generateEdges() {
        for (int i = 0; i<polygons.size(); i++){
            for (int j = 0; j<polygons.size(); j++){
                if (j != i){
                    if (polygons.get(i).isNeighbour(polygons.get(j))){
                        edgeList.add(new Edge(i, j, calcDistance(centroids.get(i), centroids.get(j))));
                    }
                }
            }
        }
    }
}
