package ca.mcmaster.cas.se2aa4.a4.pathfinder.Algorithm;

import java.util.List;
import java.util.Map;

public interface FindPath {
    Map<Integer, List<Integer>> calculatePath(int source);
}
