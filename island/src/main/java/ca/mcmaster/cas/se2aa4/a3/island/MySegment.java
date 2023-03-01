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
}
