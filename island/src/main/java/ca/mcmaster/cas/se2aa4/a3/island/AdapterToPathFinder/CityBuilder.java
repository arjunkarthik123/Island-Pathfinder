package ca.mcmaster.cas.se2aa4.a3.island.AdapterToPathFinder;
import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyPolygon;

import java.util.*;
public class CityBuilder extends PolygonGraphAdapter {
    int cities;
    public CityBuilder (List <MyPolygon> polygons, Random rand, int cities){
        super(polygons, rand);
        this.cities = cities;
        executeAdapter();
    }

    private Map <Integer, Boolean> assignCityToNode (){
        Map <Integer, Boolean> determineCities = new HashMap<>();
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
            } while (!indexList.contains(nodeIndex));
        }
        return determineCities;
    }
}
