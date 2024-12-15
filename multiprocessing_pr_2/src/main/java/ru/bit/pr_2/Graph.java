package ru.bit.pr_2;

import java.util.List;

public interface Graph {
    List<Integer> getNeighborsForNode(int node);

    int getNumberOfNodesInGraph();
}
