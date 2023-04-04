package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;

import java.util.List;

public interface FindPath {

    void calculatePath(List<List<Node>> adjacent, int source, String name);
    void calculateNeighbours (int n);
}
