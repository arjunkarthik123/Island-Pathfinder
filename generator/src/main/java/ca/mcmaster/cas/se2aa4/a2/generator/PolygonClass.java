package ca.mcmaster.cas.se2aa4.a2.generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonClass implements MyShape {
    private static int totalIndex = 0;
    private final int index;

    private List <MySegment> segments;
    private List <Integer> neighbourIndices = new ArrayList<>();
    private MyVertex centroid;

    private Polygon polygon;

    public PolygonClass (List <MySegment> segments){
        this.index = totalIndex;
        totalIndex++;
        this.segments = orderSegments(segments);
        calcCentroid();
        initPolygon();
    }
    // Constructor when centroid is specified as an input
    public PolygonClass (List <MySegment> segments, double x, double y){
        this.index = totalIndex;
        totalIndex++;
        this.segments = orderSegments(segments);
        setCentroid(x, y);
        initPolygon();
    }

    private void initPolygon() {
        Property color = Property.newBuilder().setKey("rgb_color").setValue(averageSegColours()).build();
        //pass in polygon in future uses

        polygon = Polygon.newBuilder().addAllSegmentIdxs(convertSegments()).addProperties(color).setCentroidIdx(centroid.getIndex()).build();
    }

    private String averageSegColours(){

        int[] red = new int[segments.size()];
        int[] green = new int[segments.size()];
        int[] blue = new int[segments.size()];
        int[] trans = new int[segments.size()];

        for (int i = 0; i < segments.size(); i++){
            int[] color = segments.get(i).getColor();
            red[i] = color[0];
            green[i] = color[1];
            blue[i] = color[2];

            if (color.length == 4){
                trans[i] = color[3];
            }
            else{
                trans[i] = 255;
            }

        }
        int avRed = Arrays.stream(red).sum() / red.length;
        int avGreen = Arrays.stream(green).sum() / red.length;
        int avBlue = Arrays.stream(blue).sum() / red.length;
        int avTrans = Arrays.stream(trans).sum() / red.length;

        return avRed + "," + avBlue + "," + avGreen + "," + avTrans;
    }

    private List <Integer> convertSegments (){
        //convert list of segments to indices separated by commas
        List <Integer> convert = new ArrayList<>();

        for (MySegment segment : segments) {
            convert.add(segment.getIndex());
        }
        return convert;
    }

    public MyVertex getCentroid (){
        return centroid;
    }

    private List <MySegment> orderSegments (List <MySegment> segments){
        List <MySegment> orderedSegments = new ArrayList<>();
        orderedSegments.add(segments.get(0));
        int count = 0;

        //while

        while (count < segments.size()){
            for (MySegment segment : segments) {
                if (!orderedSegments.contains(segment)) {
                    MySegment last = orderedSegments.get(orderedSegments.size() - 1);
                    if (last.isAdjacent(segment)) {
                        orderedSegments.add(segment);
                    }
                }
            }
            count++;
        }
        return orderedSegments;
    }

    private void calcCentroid (){
        double midX = 0;
        double midY = 0;
        for (MySegment segment : segments) {
            midX += segment.getMiddle()[0];
            midY += segment.getMiddle()[1];
        }
        double x = midX/segments.size();
        double y = midY/segments.size();

        centroid = new MyVertex(x,y);
    }

    public void setNeighbourIndices () {
        polygon = Polygon.newBuilder(polygon).addAllNeighborIdxs(neighbourIndices).build();
    }

    /**
     * Tells if other polygon has a neighbouring segment to this.
     * @param other other polygon we are checking against
     * @return shared segment between the two polygons
     */
    public MySegment isNeighbourSegment(PolygonClass other){

        for (int i = 0; i<other.segments.size(); i++){
            for (int j = 0; j<this.segments.size(); j++){
                if (other.segments.get(i).getIndex() == this.segments.get(j).getIndex()){
                    return this.segments.get(j);
                }
            }
        }
        return null;
    }

    public int[] getColor(){
        String val = PropertyManager.getProperty(this.getPropertiesList(), "rgb_color");
        if (val == null)
            return new int[] {0, 0, 0};
        return PropertyManager.extractColor(val);
    }

    public java.util.List<Property> getPropertiesList() {
        return polygon.getPropertiesList();
    }
    public void setTrans(int alpha){
        String colorCode = PropertyManager.getProperty(getPropertiesList(), "rgb_color");
        int[] colors = PropertyManager.extractColor(colorCode);
        String newColorCode = colors[0] + "," + colors[1] + "," + colors[2] + "," + alpha;
        Property color = Property.newBuilder().setKey("rgb_color").setValue(newColorCode).build();
        polygon = Polygon.newBuilder(polygon).setProperties(0, color).build();
    }

    public void setThick (float thickness){
        Property thick = Property.newBuilder().setKey("thickness").setValue("" + thickness).build();

        String val = PropertyManager.getProperty(this.getPropertiesList(), "thickness");

        // If thickness property does not already exist.
        if (val == null) {
            polygon = Polygon.newBuilder(polygon).addProperties(thick).build();
        }
        // If thickness value needs to be changed.
        else{
            polygon = Polygon.newBuilder(polygon).setProperties(1, thick).build();
        }
    }

    public boolean isNeighbour(PolygonClass other){
        for (int i = 0; i<other.segments.size(); i++){
            for (int j = 0; j<this.segments.size(); j++){
                if (other.segments.get(i).getIndex() == this.segments.get(j).getIndex()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Changes colour of specific segment named.
     * @param segment segment to have its colour changed in this polygon
     */
    public void changeSegColour(MySegment segment, String colorCode){
        segment.setColor(colorCode);
    }

    /**
     * Sets all the segments in the polygon to the same colour.
     * @param colorCode string colour code to set segments to
     */
    public void setSegmentsColor(String colorCode){
        for (int i = 0; i<segments.size(); i++){
            segments.get(i).setColor(colorCode);
        }
    }

    public void setPolygonColor(String colorCode){
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        polygon = Polygon.newBuilder(polygon).setProperties(0, color).build();
    }

    /**
     * Returns if the input list of segments is the same segments this instance contains.
     * @param segments list of segments we are checking against.
     * @return true if this polygon represents the input list of segments.
     */
    public boolean equals(List<MySegment> segments) {

        if (this.segments.size() != segments.size()) {
            return false;
        }
        else { //modify this such that we compare every element of this.segments to passed in segments
            for (MySegment s1 : this.segments){


                // Goes through each segment in input list, if we find one that equals what we are checking, go to next in loop.
                for (MySegment s2 : segments){
                    if (s1.getIndex() == s2.getIndex()) {
                        break;
                    }
                }

                // If we did not find a segment in input list that exists in our polygon, cannot be the same.
                return false;

            }
            return true;
        }
    }

    public void addNeighbour (int index){
        //add to list of neighbours
        if (!neighbourIndices.contains(index) && !(index == this.index)){
            neighbourIndices.add(index);
        }
    }

    public int getIndex(){
        return index;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    // Resets index count for when the hashsets in Mesh are cleared
    public static void resetCount() {
        totalIndex = 0;
    }

    // Initializes centroid given x,y
    public void setCentroid(double x, double y) {
        centroid = new MyVertex(x, y);
    }
    public boolean checkIfCentroid(double x, double y, double precision){
        return centroid.existsAtPoint(x, y, precision);
    }
}
