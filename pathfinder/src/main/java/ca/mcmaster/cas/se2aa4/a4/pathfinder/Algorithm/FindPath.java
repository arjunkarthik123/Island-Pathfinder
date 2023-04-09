package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;

import java.util.List;
import java.util.Set;

public interface FindPath {

    List<List<Integer>> calculatePath(List<List<Node>> adjacent, int source);
    void calculateNeighbours (int n);
}
