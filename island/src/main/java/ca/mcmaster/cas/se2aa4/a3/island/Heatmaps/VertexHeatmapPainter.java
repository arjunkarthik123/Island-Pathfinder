package ca.mcmaster.cas.se2aa4.a3.island.Heatmaps;

import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyPolygon;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MySegment;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyVertex;

import java.awt.*;
import java.util.List;

public abstract class VertexHeatmapPainter extends HeatmapPainter {

    public void createHeatmap(List<MyPolygon> myPolygons, List<MyVertex> myVertices, List<MySegment> mySegments){
        normalizeShapeColor(mySegments, "0,0,0");
        normalizeShapeColor(myPolygons, "0,0,0");
        for (MyVertex v : myVertices){
            Color newColor = determineColor(v);
            v.changeColor(newColor.getRed() + "," + newColor.getGreen() + "," + newColor.getBlue());
        }
    }

}
