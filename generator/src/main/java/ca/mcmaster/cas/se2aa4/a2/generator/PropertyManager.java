package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class PropertyManager {

    public static String getProperty(java.lang.Iterable<? extends Property> values,
                                     String key){
        String val = null;
        for(Property p: values) {
            if (p.getKey().equals(key)) {
                val = p.getValue();
            }
        }
        return val;
    }

    public static int[] extractColor(String val){
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new int[]{red, green, blue};
    }
}
