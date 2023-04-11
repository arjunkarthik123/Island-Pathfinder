package ca.mcmaster.cas.se2aa4.a3.island.Builders;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a3.island.Heatmaps.HeatmapPainter;

import java.util.Random;

public interface MeshBuilder {

    void buildIsland(Mesh aMesh, Random rand, int aquiferNum, int numLakes, int numRivers, int cities);
    Mesh getIsland();
    void applyHeatmap(HeatmapPainter painter);

}
