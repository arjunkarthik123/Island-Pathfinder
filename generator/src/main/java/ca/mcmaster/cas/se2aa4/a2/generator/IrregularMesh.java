package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.*;

public class IrregularMesh extends MyMesh{

    private final int NUM_POLYGONS;
    private final Random rand = new Random();
    private final int RELAXATION_LEVEL;
    private Set<Coordinate> voronoiPoints = new LinkedHashSet<>();


    public IrregularMesh(int numPolygons, int relaxation){
        NUM_POLYGONS = numPolygons;
        RELAXATION_LEVEL = relaxation;
    }

    public Structs.Mesh buildMesh(int polyTrans, int segTrans, int vertexTrans, float polyThick, float segThick,
                                  float vertexThick) {

        Set<MyVertex> myVertices = new LinkedHashSet<>();
        Set<MySegment> mySegments = new LinkedHashSet<>();
        Set<PolygonClass> myPolygons = new LinkedHashSet<>();


        myPolygons = createVoronoiSegNPoly(myVertices, mySegments, myPolygons);
        setAllNeighbours(myPolygons);

        setShapeTrans(myPolygons, polyTrans);
        setShapeTrans(mySegments, segTrans);
        setShapeTrans(myVertices, vertexTrans);
        setShapeThick(myPolygons, polyThick);
        setShapeThick(mySegments, segThick);
        setShapeThick(myVertices, vertexThick);

        Set<Structs.Vertex> vertices = extractVertices(myVertices);
        Set<Structs.Segment> segments = extractSegments(mySegments);
        Set<Structs.Polygon> polygons = extractPolygons(myPolygons);
        return Structs.Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();

    }

    // These points are only relevant to the original generation of the irregular mesh
    // Generates random Coordinate points and saves in hashset for initial generation of voronoi diagram
    private void createRandomPoints() {
        int x = -1;
        int y = -1;
        Coordinate point;

        // Generates a random vertex for each polygon
        for (int i = 0; i < NUM_POLYGONS; i++) {
            // Keep generating random x and y until a unique coordinate is found
            while ((x == -1 && y == -1) || isDuplicatePoint(x, y)) {
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }
            point = new Coordinate(x, y);

            voronoiPoints.add(point);
        }
    }
    // RIGHT NOW ONLY INTEGERS
    // Generates the voronoi diagram Geometry object
    private List<Geometry> createVoronoiAboutPoints() {
        List<Geometry> polyList = new ArrayList<>();
        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();
        GeometryFactory factory = new GeometryFactory();

        voronoi.setSites(voronoiPoints);

        Geometry g =  voronoi.getDiagram(factory); // creates voronoi

        // Specifies size of canvas
        Envelope envelope = new Envelope(0, width, 0, height);
        g = g.intersection(factory.toGeometry(envelope));


        // Adding all geometries that make the voronoi to an arraylist
        for (int k = 0; k < g.getNumGeometries(); k++) {
            polyList.add(g.getGeometryN(k));
        }
        return polyList;
    }

    // Converts the voronoi diagram object into vertices, segments and polygons (from JTS to io Structs)
    private Set<PolygonClass> createVoronoiSegNPoly(Set<MyVertex> myVertices, Set<MySegment> mySegments, Set<PolygonClass> myPolygons) {
        createRandomPoints();
        List<Geometry> polygons = createVoronoiAboutPoints();
        Coordinate[] polyCoords;
        MyVertex v1, v2;
        MySegment s;
        Coordinate c1, c2;
        int count = 0;

        do {
            for (Geometry p : polygons) {
                polyCoords = p.getCoordinates();
                // Create 2 segments at a time by looking at 2 coordinates at once -> coordinates correspond to every vertex in the polygon
                ArrayList<MySegment> polySegments = new ArrayList<>();
                for (int i = 0; i < polyCoords.length; i++) {
                    // Gets Coordinate pair
                    c1 = polyCoords[i];
                    // Handles edge case of the last point not having a + 1 index
                    // Last point should connect to the originally FIRST point
                    if (i == polyCoords.length - 1) {
                        c2 = polyCoords[0];
                    } else {
                        c2 = polyCoords[i + 1];
                    }

                    // Checks if (x,y) pair is a preexisting vertex -> will make new one if not
                    v1 = findVertex(myVertices, c1.getX(), c1.getY());
                    v2 = findVertex(myVertices, c2.getX(), c2.getY());

                    myVertices.add(v1);
                    myVertices.add(v2);

                    s = findSegment(mySegments, v1, v2);

                    mySegments.add(s);
                    polySegments.add(s);
                }

                if (polygonDoesNotExist(myPolygons, polySegments) && polySegments.size() > 0) {
                    PolygonClass polygon = new PolygonClass(polySegments, p.getCentroid().getX(), p.getCentroid().getY());
                    myPolygons.add(polygon);
                    myVertices.add(polygon.getCentroid()); // Don't want to add this until the last relaxed centroid
                }
            }
            // Continue to compute the voronoi diagram -> as a part of lloyd relaxation
            // Until the relaxation level is reached
            count++;

            // Make sure its not the last relaxation level
            if (count != RELAXATION_LEVEL) {
                // Reset the collections in preparation of the next voronoi generation
                MyVertex.resetCount();
                MySegment.resetCount();
                myVertices.clear();
                mySegments.clear();
                polygons = relaxLloyd(myPolygons);
                PolygonClass.resetCount();
                myPolygons.clear();
            }

        } while (count < RELAXATION_LEVEL);

        return myPolygons;
    }

    // Generates a new set of voronoiPoints and voronoi diagram given the previously generated polygon centroids
    private List<Geometry> relaxLloyd(Set<PolygonClass> myPolygons) {
        voronoiPoints = new LinkedHashSet<>();
        MyVertex centroidVertex;
        Coordinate newPoint;
        // Compute the new voronoi diagram with the set of voronoi points
        return createVoronoiAboutPoints();
    }

    private boolean isDuplicatePoint(double x, double y) {
        for (Coordinate point : voronoiPoints) {
            if (point.getX() == x && point.getY() == y) { // HAVE NOT IMPLEMENTED PRECISION YET
                return true;
            }
        }
        return false;
    }

}