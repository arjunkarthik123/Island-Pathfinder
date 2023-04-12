package ca.mcmaster.cas.se2aa4.a3.island.AdapterToPathFinder;

import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyPolygon;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MySegment;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyVertex;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Edge;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public abstract class PolygonGraphAdapter implements AdapterProfile {

    protected final List<MyPolygon> polygons;
    protected final List<MySegment> segments;
    protected final List<MyVertex> vertices;
    protected final List<Point> centroids = new ArrayList<>();
    protected Graph graph;
    protected final List <Node> nodeList = new ArrayList<>();
    protected final List<Edge> edgeList = new ArrayList<>();
    Random rand;
    public PolygonGraphAdapter (List<MyPolygon> polygons, Random rand, List<MyVertex> vertices, List<MySegment> segments) {
        this.polygons = polygons;
        this.segments = segments;
        this.vertices = vertices;
        this.rand = rand;
    }
    protected int calcDistance(Point p1, Point p2){
        double d1 = Math.pow((p1.getX() - p2.getX()), 2);
        double d2 = Math.pow(p1.getY() - p2.getY(), 2);
        return (int) Math.pow((d1 + d2), 0.5);
    }
    private void getAllCentroids(){
        for (MyPolygon myPolygon : polygons) {
            centroids.add(myPolygon.getCenterOfPolygon());
        }
    }
    @Override
    public void generateNodes(){
        for (int i = 0; i<centroids.size(); i++){
            nodeList.add(new Node(i, Integer.MAX_VALUE));
        }
    }
    public void executeAdapter(){
        getAllCentroids();
        generateNodes();
    }
}
