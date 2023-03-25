package ca.mcmaster.cas.se2aa4.a3.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class MySegment implements MyShape{

    private static int totalIndex = 0;
    private final int index;
    private Segment segment;
    private MyVertex v1, v2;

    public MySegment(Segment s){
        segment = s;
        index = totalIndex;
        totalIndex++;
    }

    public void changeColor(String colorCode){
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        segment = Segment.newBuilder(segment).setProperties(0, color).build();
    }

    // Setters
    public void setV1(MyVertex v1) {
        this.v1 = v1;
    }
    public void setV2(MyVertex v2) {
        this.v2 = v2;
    }

    /**
     * Returns the index of the comment vertex between segments.
     * @param other other segment to be compared against.
     * @return the index of the common vertex, 0 if not adjacent.
     */
    public int isAdjacent(MySegment other){
        if (other.getV1Index() == this.getV1Index() && other.getV2Index() != this.getV2Index())
            return getV1Index();
        if (other.getV1Index() != this.getV1Index() && other.getV2Index() == this.getV2Index())
            return getV2Index();
        if (other.getV1Index() == this.getV2Index() && other.getV2Index() != this.getV1Index())
            return getV2Index();
        if (other.getV1Index() != this.getV2Index() && other.getV2Index() == this.getV1Index())
            return getV1Index();
        return -1;
    }

    // Getters
    public int getIndex(){ return index; }
    public int getV1Index(){
        return segment.getV1Idx();
    }
    public int getV2Index(){
        return segment.getV2Idx();
    }
    public java.util.List<Structs.Property> getPropertiesList() {
        return segment.getPropertiesList();
    }
    public Segment getSegment() { return segment; }
    public double getV1X(){ return v1.getX(); }
    public double getV1Y(){ return v1.getY(); }
    public double getV2X(){ return v2.getX(); }
    public double getV2Y(){ return v2.getY(); }

    public MyVertex getV1(){return v1;}
    public MyVertex getV2(){return v2;}
}
