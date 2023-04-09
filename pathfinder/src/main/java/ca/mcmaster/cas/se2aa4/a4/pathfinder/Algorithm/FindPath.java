package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Graph;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT.Node;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FindPath {

    Map<Integer, List<Integer>> calculatePath(Graph graph, int source);
}
